package com.thumbing.contentserver.xxljob;

import com.thumbing.contentserver.cache.RoastCache;
import com.thumbing.shared.entity.mongo.BaseMongoEntity;
import com.thumbing.shared.entity.mongo.content.Roast;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    private MongoTemplate mongoTemplate;

    @XxlJob("roastChangeHandler")
    public ReturnT<String> execute(String param){
        Set<String> set1 = roastCache.getAndClearThumbChangedSet();
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> changeThumbsNum(set1));
        task1.join();
        return ReturnT.SUCCESS;
    }

    private void changeThumbsNum(Set<String> set){
        if(CollectionUtils.isNotEmpty(set)) {
            set.parallelStream().forEach(id->{
                int thumbs = roastCache.getThumbsNum(id);
                Set<Long> userIds = roastCache.getThumbUserIds(id);
                Query query = Query.query(Criteria.where(BaseMongoEntity.Fields.id).is(id));
                Update update = Update.update(Roast.Fields.thumbingNum, thumbs)
                        .set(Roast.Fields.thumbUserIds, userIds);
                mongoTemplate.updateFirst(query, update, Roast.class);
            });
        }
    }
}
