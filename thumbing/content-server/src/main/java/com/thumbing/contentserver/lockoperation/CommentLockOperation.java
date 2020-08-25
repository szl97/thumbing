package com.thumbing.contentserver.lockoperation;

import com.thumbing.contentserver.cache.CommentCache;
import com.thumbing.contentserver.dto.input.FetchCommentInput;
import com.thumbing.shared.annotation.AccessLock;
import com.thumbing.shared.entity.mongo.MongoCreationEntity;
import com.thumbing.shared.entity.mongo.content.Comment;
import com.thumbing.shared.repository.mongo.content.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/25 13:53
 */
@Component
public class CommentLockOperation {
    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private CommentCache commentCache;

    @AccessLock(value = "com.thumbing.shared.entity.mongo.content.Comment",
                className = "com.thumbing.contentserver.dto.input.FetchCommentInput",
                fields = {"getContentId","getContentType"})
    public Boolean getComments(FetchCommentInput input){
        Sort sort = Sort.by(Sort.Direction.DESC, MongoCreationEntity.Fields.createTime);
        List<Comment> comments = commentRepository.findAllByContentIdAndContentType(input.getContentId(), input.getContentType(), sort);
        comments.stream().forEach(
                comment -> {
                    commentCache.storeComments(comment);
                }
        );
        return true;
    }
}
