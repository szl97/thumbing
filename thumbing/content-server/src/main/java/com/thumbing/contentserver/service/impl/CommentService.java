package com.thumbing.contentserver.service.impl;

import com.github.dozermapper.core.Mapper;
import com.thumbing.contentserver.cache.ArticleCache;
import com.thumbing.contentserver.cache.CommentCache;
import com.thumbing.contentserver.cache.MomentsCache;
import com.thumbing.contentserver.cache.NickNameCache;
import com.thumbing.contentserver.dto.input.*;
import com.thumbing.contentserver.dto.output.ChildCommentDto;
import com.thumbing.contentserver.dto.output.CommentDto;
import com.thumbing.contentserver.lockoperation.ArticleLockOperation;
import com.thumbing.contentserver.lockoperation.CommentLockOperation;
import com.thumbing.contentserver.lockoperation.MomentsLockOperation;
import com.thumbing.contentserver.lockoperation.NickNameLockOperation;
import com.thumbing.contentserver.sender.PushDataSender;
import com.thumbing.contentserver.service.ICommentService;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.entity.mongo.content.Article;
import com.thumbing.shared.entity.mongo.content.Comment;
import com.thumbing.shared.entity.mongo.content.Moments;
import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import com.thumbing.shared.entity.mongo.record.PushDataRecord;
import com.thumbing.shared.exception.BusinessException;
import com.thumbing.shared.message.PushDataTypeEnum;
import com.thumbing.shared.repository.mongo.content.ICommentRepository;
import com.thumbing.shared.service.impl.BaseMongoService;
import com.thumbing.shared.utils.dozermapper.DozerUtils;
import com.thumbing.shared.utils.generateid.SnowFlake;
import com.thumbing.shared.utils.sensitiveword.SensitiveFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Stan Sai
 * @date 2020-08-26 5:07
 */
@Service
@Transactional
public class CommentService extends BaseMongoService<Comment, ICommentRepository> implements ICommentService {
    @Autowired
    private CommentCache commentCache;
    @Autowired
    private ArticleCache articleCache;
    @Autowired
    private MomentsCache momentsCache;
    @Autowired
    private NickNameCache nickNameCache;
    @Autowired
    private CommentLockOperation lockOperation;
    @Autowired
    private NickNameLockOperation nickNameLockOperation;
    @Autowired
    private ArticleLockOperation articleLockOperation;
    @Autowired
    private MomentsLockOperation momentsLockOperation;
    @Autowired
    private PushDataSender sender;
    @Autowired
    private Mapper mapper;

    @Override
    public Boolean publishComment(CommentInput input, UserContext context) {
        SensitiveFilter filter = SensitiveFilter.DEFAULT;
        input.setContent(filter.filter(input.getContent(),'*'));
        Comment comment = mapper.map(input, Comment.class);
        if (input.getContentType() == ContentType.ARTICLE) {
            ArticleIdInput idInput = new ArticleIdInput();
            idInput.setId(input.getContentId());
            existArticle(idInput);
            Article article = articleCache.getArticleNoChangedInfo(input.getContentId());
            if(article.getUserId().equals(context.getId())){
                input.setFromNickName("作者");
            } else {
                int currentSeq = getArticleCurrentSeq(idInput);
                String userNickName;
                if (currentSeq > 0) {
                    userNickName = getArticleUserNickName(idInput, context.getId());
                } else {
                    int seq = articleCache.getNickNameSeq(input.getContentId());
                    userNickName = getNickName(seq);
                }
                comment.setFromNickName(userNickName);
            }
        }else {
            MomentsIdInput idInput = new MomentsIdInput();
            idInput.setId(input.getContentId());
            existMoments(idInput);
            Moments moments = momentsCache.getMomentsNoChangedInfo(input.getContentId());
            if(moments.getUserId().equals(context.getId())){
                input.setFromNickName("楼主");
            } else{
                int currentSeq = getMomentsCurrentSeq(idInput);
                String userNickName;
                if (currentSeq > 0) {
                    userNickName = getMomentsUserNickName(idInput, context.getId());
                } else {
                    int seq = momentsCache.getNickNameSeq(input.getContentId());
                    userNickName = getNickName(seq);
                }
                comment.setFromNickName(userNickName);
            }
        }
        storeCommentsInRedis(input);
        comment.setFromUserId(context.getId());
        comment.setCreateTime(LocalDateTime.now());
        comment.setCommentId(SnowFlake.getInstance().nextId());
        commentCache.addComments(comment);
        //todo:发送到消息队列，record-server和data-center接收
        PushDataRecord msg = new PushDataRecord();
        msg.setDataId(comment.getCommentId().toString());
        msg.setData(comment.getContent());
        msg.setRead(false);
        msg.setCreateTime(comment.getCreateTime());
        msg.setPushType(comment.getContentType() == ContentType.ARTICLE ? PushDataTypeEnum.AC : PushDataTypeEnum.MC);
        msg.setFromUserId(context.getId());
        msg.setFromUserName(context.getName());
        msg.setFromUserNickName(comment.getFromNickName());
        msg.setToUserId(comment.getToUserId());
        sender.sendComment(msg);
        return true;
    }

    @Override
    public List<CommentDto> fetchComments(FetchCommentInput input, UserContext context) {
        int commentNum;
        if(input.getContentType()==ContentType.ARTICLE){
            ArticleIdInput idInput = new ArticleIdInput();
            idInput.setId(input.getContentId());
            commentNum = getArticleCommentsNum(idInput);
        }
        else {
            MomentsIdInput idInput = new MomentsIdInput();
            idInput.setId(input.getContentId());
            commentNum = getMomentsCommentsNum(idInput);
        }
        if(commentNum > 0) {
            storeCommentsInRedis(input);
        }
        if(input.getContentType()==ContentType.ARTICLE) {
            if (!commentCache.existArticleComments(input.getContentId())){
                return null;
            }
        }
        else{
            if (!commentCache.existMomentsComments(input.getContentId())){
                return null;
            }
        }
        List<Comment> parentComments = commentCache.getParentsCommentsList(input.getContentId(), input.getContentType(), 0, -1);
        List<CommentDto> result = DozerUtils.mapListSync(mapper, parentComments, CommentDto.class);
        result.parallelStream().forEach(
                parent->{
                    if(commentCache.existChildComments(parent.getCommentId())) {
                        List<Comment> child = commentCache.getChildCommentsList(parent.getCommentId(), 0, -1);
                        parent.setChildComments(DozerUtils.mapListSync(mapper, child, ChildCommentDto.class));
                    }
                }
        );
        return result;
    }

    @Override
    public Boolean deleteComment(CommentIdInput input, UserContext context) {
        boolean b = false;
        if(input.getContentType() == ContentType.ARTICLE){
            if(commentCache.existArticleComments(input.getContentId())){
                b = true;
            }
        }
        else{
            if(commentCache.existMomentsComments(input.getContentId())){
                b = true;
            }
        }
        if(b) {
            Comment comment = commentCache.getCommentNoChangedInfo(input.getId());
            if (comment != null) {
                commentCache.deleteComments(input.getId());
                return true;
            }
        }
        repository.updateIsDeleteByCommentId(input.getId());
        return true;
    }

    @Override
    public Boolean thumbComment(ThumbCommentInput input, UserContext context) {
        Long userId = confirmCommentsThumbsInRedis(input);
        commentCache.thumbsChanged(input.getId(), context.getId(), input.isAdd());
        //todo:发送到消息队列，record-server和data-center接收
        PushDataRecord msg = new PushDataRecord();
        msg.setDataId(input.getId().toString());
        msg.setRead(false);
        msg.setCreateTime(LocalDateTime.now());
        msg.setToUserId(userId);
        msg.setPushType(PushDataTypeEnum.CT);
        sender.sendThumb(msg);
        return true;
    }

    private Boolean existArticle(ArticleIdInput idInput){
        if(articleCache.existArticleInfo(idInput.getId())){
            return true;
        }
        if(articleLockOperation.getArticle(idInput) == null) existArticle(idInput);
        return true;
    }

    private Boolean existMoments(MomentsIdInput idInput){
        if(momentsCache.existMomentsInfo(idInput.getId())){
            return true;
        }
        if(momentsLockOperation.getMoments(idInput) == null) existMoments(idInput);
        return true;
    }

    private String getNickName(int seq){
        if(nickNameCache.nickNameListExist()){
            return nickNameCache.getNickName(seq);
        }
        nickNameLockOperation.storeAllNickNames();
        return getNickName(seq);
    }

    private int getArticleCurrentSeq(ArticleIdInput idInput){
        if(articleCache.existArticleInfo(idInput.getId())){
            return articleCache.getCurrentNickNameSeq(idInput.getId());
        }
        Article article = articleLockOperation.getArticle(idInput);
        if(article == null) return  getArticleCurrentSeq(idInput);
        return article.getNickNameSequence();
    }

    private int getMomentsCurrentSeq(MomentsIdInput idInput){
        if(momentsCache.existMomentsInfo(idInput.getId())){
            return momentsCache.getCurrentNickNameSeq(idInput.getId());
        }
        Moments moments = momentsLockOperation.getMoments(idInput);
        if (moments == null) return getMomentsCommentsNum(idInput);
        return moments.getNickNameSequence();
    }

    private int getArticleCommentsNum(ArticleIdInput idInput) {
        if (articleCache.existArticleInfo(idInput.getId())) {
            return articleCache.getArticleCommentsNum(idInput.getId());
        }
        Article article = articleLockOperation.getArticle(idInput);
        if (article == null) return getArticleCommentsNum(idInput);
        return article.getCommentsNum() == null ? 0 : article.getCommentsNum();
    }

    private int getMomentsCommentsNum(MomentsIdInput idInput) {
        if (momentsCache.existMomentsInfo(idInput.getId())) {
            return articleCache.getArticleCommentsNum(idInput.getId());
        }
        Moments moments = momentsLockOperation.getMoments(idInput);
        if (moments == null) return getMomentsCommentsNum(idInput);
        return moments.getCommentsNum() == null ? 0 : moments.getCommentsNum();
    }

    private String getArticleUserNickName(ArticleIdInput idInput, Long userId){
        if(nickNameCache.userNickNameArticleExist(idInput.getId())){
            String userNickName = nickNameCache.getUserNickNameArticle(idInput.getId(), userId);
            if(StringUtils.isBlank(userNickName)) {
                int seq = articleCache.getNickNameSeq(idInput.getId());
                userNickName = getNickName(seq);
            }
            return userNickName;
        }
        nickNameLockOperation.storeArticleNickName(idInput);
        return getArticleUserNickName(idInput, userId);
    }

    private String getMomentsUserNickName(MomentsIdInput idInput, Long userId){
        if(nickNameCache.userNickNameMomentsExist(idInput.getId())){
            String userNickName = nickNameCache.getUserNickNameMoments(idInput.getId(), userId);
            if(StringUtils.isBlank(userNickName)) {
                int seq = momentsCache.getNickNameSeq(idInput.getId());
                userNickName = getNickName(seq);
            }
            return userNickName;
        }
        nickNameLockOperation.storeMomentsNickName(idInput);
        return getMomentsUserNickName(idInput, userId);
    }

    private Boolean storeCommentsInRedis(FetchCommentInput input){
        if(input.getContentType() == ContentType.ARTICLE){
            if(commentCache.existArticleComments(input.getContentId())){
                return true;
            }
        }
        else{
            if(commentCache.existMomentsComments(input.getContentId())){
                return true;
            }
        }
        lockOperation.getComments(input);
        return storeCommentsInRedis(input);
    }

    private Long confirmCommentsThumbsInRedis(CommentIdInput input) {
        if (!(commentCache.existThumbingUser(input.getId()) && commentCache.existCommentThumbsNum(input.getId()))) {
            if(lockOperation.getCommentDetails(input) == null) return confirmCommentsThumbsInRedis(input);
        }
        Comment comment = commentCache.getCommentNoChangedInfo(input.getId());
        if(comment == null) throw new BusinessException("评论未找到");
        return comment.getFromUserId();
    }
}
