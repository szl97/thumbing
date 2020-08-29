package com.thumbing.contentserver.xxljob;

import cn.hutool.core.util.ArrayUtil;
import com.thumbing.contentserver.cache.RoastCache;
import com.thumbing.shared.repository.mongo.content.IRoastRepository;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/27 16:42
 */
@Component
public class RoastChangeXxlJob {
    @Autowired
    private RoastCache roastCache;
    @Autowired
    private IRoastRepository roastRepository;

    @XxlJob("commentsChangeHandler")
    public ReturnT<String> execute(String param){
        Set<String> set1 = roastCache.getAndClearThumbChangedSet();
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> changeThumbsNum(set1));
        task1.join();
        return ReturnT.SUCCESS;
    }

    private void changeThumbsNum(Set<String> set){
        if(ArrayUtil.isNotEmpty(set)) {
            set.parallelStream().forEach(id->{
                int thumbs = roastCache.getThumbsNum(id);
                Set<Long> userIds = roastCache.getThumbUserIds(id);
                roastRepository.updateThumbingNumAndThumbUserIdsById(id, thumbs, userIds);
            });
        }
    }
}
