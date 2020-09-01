package com.thumbing.contentserver.xxljob;

import cn.hutool.core.util.ArrayUtil;
import com.thumbing.contentserver.cache.CommentCache;
import com.thumbing.shared.entity.mongo.MongoFullAuditedEntity;
import com.thumbing.shared.entity.mongo.content.Comment;
import com.thumbing.shared.repository.mongo.content.ICommentRepository;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/21 16:18
 */
@Component
public class CommentsChangeXxlJob {
    @Autowired
    private CommentCache commentCache;
    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @XxlJob("commentsChangeHandler")
    public ReturnT<String> execute(String param){
        List<Comment> newComments = commentCache.getAndClearNewComments();
        commentRepository.saveAll(newComments);
        Set<Long> set1 = commentCache.getAndClearThumbsNumChanged();
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> changeThumbsNum(set1));
        Set<Long> set2 = commentCache.getAndClearDeletedCommentsId();
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(()-> deleteComments(set2));
        CompletableFuture.allOf(task1, task2).join();
        return ReturnT.SUCCESS;
    }

    private void changeThumbsNum(Set<Long> set){
        if(ArrayUtil.isNotEmpty(set)) {
            set.parallelStream().forEach(id->{
                int thumbs = commentCache.getThumbsNum(id);
                Set<Long> userIds = commentCache.getThumbUserIds(id);
                Query query = Query.query(Criteria.where(Comment.Fields.commentId).is(id));
                Update update = Update.update(Comment.Fields.thumbingNum, thumbs)
                        .set(Comment.Fields.thumbUserIds, userIds);
                mongoTemplate.updateFirst(query, update, Comment.class);
            });
        }
    }

    private void deleteComments(Set<Long> set){
        if(ArrayUtil.isNotEmpty(set)){
            set.parallelStream().forEach(id->{
                Query query = Query.query(Criteria.where(Comment.Fields.commentId).is(id));
                Update update = Update.update(MongoFullAuditedEntity.Fields.isDelete, 1);
                mongoTemplate.updateFirst(query, update, Comment.class);
            });
        }
    }
}
