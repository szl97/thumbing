package com.thumbing.contentserver.service.impl;

import com.github.dozermapper.core.Mapper;
import com.thumbing.contentserver.cache.ArticleCache;
import com.thumbing.contentserver.cache.CommentCache;
import com.thumbing.contentserver.cache.MomentsCache;
import com.thumbing.contentserver.cache.NickNameCache;
import com.thumbing.contentserver.dto.input.ArticleIdInput;
import com.thumbing.contentserver.dto.input.CommentIdInput;
import com.thumbing.contentserver.dto.input.CommentInput;
import com.thumbing.contentserver.dto.input.FetchCommentInput;
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
        comment.setFromUserId(context.getId());
        if (comment.getFromNickName() == null) {
            if (input.getContentType() == ContentType.ARTICLE) {
                ArticleIdInput idInput = new ArticleIdInput();
                idInput.setId(input.getContentId());
                int commentsNum = getArticleCommentsNum(idInput);
                String userNickName;
                if(commentsNum > 0){
                    userNickName = getArticleUserNickName(idInput, context.getId());
                }
                else {
                    int seq = articleCache.getNickNameSeq(input.getContentId());
                    userNickName = getNickName(seq);
                }
                comment.setFromNickName(userNickName);
                comment.setCreateTime(LocalDateTime.now());
                comment.setCommentId(SnowFlake.getInstance().nextId());
                commentCache.addComments(comment);
            } else {
                //todo: moments

            }
        }
        return null;
    }

    @Override
    public List<CommentDto> fetchComments(FetchCommentInput input, UserContext context) {
        return null;
    }

    @Override
    public Boolean deleteComment(CommentIdInput input, UserContext context) {
        return null;
    }

    private String getNickName(int seq){
        if(nickNameCache.nickNameListExist()){
            return nickNameCache.getNickName(seq);
        }
        nickNameLockOperation.storeAllNickNames();
        return getNickName(seq);
    }

    private int getArticleCommentsNum(ArticleIdInput idInput){
        if(articleCache.existArticleInfo(idInput.getId())){
            return articleCache.getArticleCommentsNum(idInput.getId());
        }
        Article article = articleLockOperation.getArticle(idInput);
        if(article == null) return  getArticleCommentsNum(idInput);
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
}
