package com.thumbing.contentserver.xxljob;

import cn.hutool.core.util.ArrayUtil;
import com.thumbing.contentserver.cache.CommentCache;
import com.thumbing.shared.entity.mongo.content.Comment;
import com.thumbing.shared.repository.mongo.content.ICommentRepository;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
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

    @XxlJob("commentsChangeHandler")
    public ReturnT<String> execute(String param){
        List<Comment> newComments = commentCache.getAndClearNewComments();
        commentRepository.saveAll(newComments);
        Set<Long> set1 = commentCache.getAndClearThumbsNumChanged();
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> changeThumbsNum(set1));
        Set<Long> set2 = commentCache.getAndClearDeletedCommentsId();
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(()-> deleteComments(set2));
        CompletableFuture.allOf(task1, task2);
        return ReturnT.SUCCESS;
    }

    private void changeThumbsNum(Set<Long> set){
        if(ArrayUtil.isNotEmpty(set)) {
            set.parallelStream().forEach(id->{
                int thumbs = commentCache.getThumbsNum(id);
                Set<Long> userIds = commentCache.getThumbUserIds(id);
                commentRepository.updateThumbingNumAndThumbUserIdsByCommentId(id, thumbs, userIds);
            });
        }
    }

    private void deleteComments(Set<Long> set){
        if(ArrayUtil.isNotEmpty(set)){
            set.parallelStream().forEach(id->commentRepository.updateIsDeleteByCommentId(id));
        }
    }
}
