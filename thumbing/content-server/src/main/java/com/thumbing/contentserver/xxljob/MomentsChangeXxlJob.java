package com.thumbing.contentserver.xxljob;

import cn.hutool.core.util.ArrayUtil;
import com.thumbing.contentserver.cache.MomentsCache;
import com.thumbing.shared.repository.mongo.content.IMomentsRepository;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/24 16:05
 */
public class MomentsChangeXxlJob {
    @Autowired
    private MomentsCache momentsCache;
    @Autowired
    private IMomentsRepository momentsRepository;

    @XxlJob("momentsChangeHandler")
    public ReturnT<String> execute(String param){
        Set<String> set1 = momentsCache.getAndClearThumbsOrCommentsChangedSet();
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> changeCommentsNumAndThumbsNumInMongo(set1));
        Set<String> set2 = momentsCache.getAndClearContentChangedSet();
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(()->changeContent(set2));
        CompletableFuture.allOf(task1, task2);
        return ReturnT.SUCCESS;
    }

    private void changeCommentsNumAndThumbsNumInMongo(Set<String> set){
        if(ArrayUtil.isNotEmpty(set)) {
            set.parallelStream().forEach(
                    id -> {
                        List<Integer> integers = momentsCache.getCommentsNumAndThumbsNum(id);
                        if (ArrayUtil.isNotEmpty(integers) && integers.size() > 1) {
                            int commentsNum = integers.get(0) == null ? 0 : integers.get(0);
                            int thumbsNum = integers.get(1) == null ? 0 : integers.get(1);
                            int seq = momentsCache.getCurrentNickNameSeq(id);
                            Set<Long> users = momentsCache.getThumbUserIds(id);
                            momentsRepository.updateNickUserSequenceAndCommentsNumAndThumbingNumAndThumbUserIdsById(id, seq, commentsNum, thumbsNum, users);
                        }
                    }
            );
        }
    }

    private void changeContent(Set<String> set){
        if(ArrayUtil.isNotEmpty(set)) {
            set.parallelStream().forEach(
                    id -> {
                        String content = momentsCache.getContent(id);
                        if (StringUtils.isNotBlank(content)) {
                            CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> momentsRepository.updateContentById(id, content));
                            task1.join();
                        }
                    }
            );
        }
    }
}
