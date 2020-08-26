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
import com.thumbing.contentserver.lockoperation.NickNameLockOperation;
import com.thumbing.contentserver.service.ICommentService;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.entity.mongo.content.Article;
import com.thumbing.shared.entity.mongo.content.Comment;
import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import com.thumbing.shared.repository.mongo.content.ICommentRepository;
import com.thumbing.shared.service.impl.BaseMongoService;
import com.thumbing.shared.utils.dozermapper.DozerUtils;
import com.thumbing.shared.utils.generateid.SnowFlake;
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
    private Mapper mapper;

    @Override
    public Boolean publishComment(CommentInput input, UserContext context) {
        Comment comment = mapper.map(input, Comment.class);
        if (input.getContentType() == ContentType.ARTICLE) {
            ArticleIdInput idInput = new ArticleIdInput();
            idInput.setId(input.getContentId());
            existArticle(idInput);
            if (comment.getFromNickName() == null) {
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
            //todo: moments

        }

        int commentsNum = input.getContentType() == ContentType.ARTICLE ? articleCache.getArticleCommentsNum(input.getContentId()) : momentsCache.getMomentsCommentsNum(input.getContentId());
        if(commentsNum > 0) storeCommentsInRedis(input);
        comment.setFromUserId(context.getId());
        comment.setCreateTime(LocalDateTime.now());
        comment.setCommentId(SnowFlake.getInstance().nextId());
        commentCache.addComments(comment);
        return true;
    }

    @Override
    public List<CommentDto> fetchComments(FetchCommentInput input, UserContext context) {
        int commentNum = 0;
        if(input.getContentType()==ContentType.ARTICLE){
            ArticleIdInput idInput = new ArticleIdInput();
            idInput.setId(input.getContentId());
            commentNum = getArticleCommentsNum(idInput);
        }
        else {

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

    private Boolean existArticle(ArticleIdInput idInput){
        if(articleCache.existArticleInfo(idInput.getId())){
            return true;
        }
        if(articleLockOperation.getArticle(idInput) == null) existArticle(idInput);
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

    private int getArticleCommentsNum(ArticleIdInput idInput) {
        if (articleCache.existArticleInfo(idInput.getId())) {
            return articleCache.getArticleCommentsNum(idInput.getId());
        }
        Article article = articleLockOperation.getArticle(idInput);
        if (article == null) return getArticleCommentsNum(idInput);
        return article.getCommentsNum() == null ? 0 : article.getCommentsNum();
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
}
