package com.thumbing.contentserver.xxljob;

import com.thumbing.contentserver.cache.ArticleCache;
import com.thumbing.shared.entity.mongo.BaseMongoEntity;
import com.thumbing.shared.entity.mongo.content.Article;
import com.thumbing.shared.entity.mongo.content.ArticleContent;
import com.thumbing.shared.repository.mongo.content.IArticleContentRepository;
import com.thumbing.shared.repository.mongo.content.IArticleRepository;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
 * XxlJob（Bean模式）
 * 开发步骤：
 * 1、在Spring Bean实例中，开发Job方法，方式格式要求为 "public ReturnT<String> execute(String param)"
 * 2、为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 3、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 * @Author: Stan Sai
 * @Date: 2020/8/20 17:31
 */
@Component
public class ArticleChangeXxlJob {
    @Autowired
    private ArticleCache articleCache;
    @Autowired
    private IArticleRepository articleRepository;
    @Autowired
    private IArticleContentRepository articleContentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @XxlJob("articleChangeHandler")
    public ReturnT<String> execute(String param){
        Set<String> set1 = articleCache.getAndClearThumbsOrCommentsChangedSet();
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> changeCommentsNumAndThumbsNumInMongo(set1));
        Set<String> set2 = articleCache.getAndClearContentChangedSet();
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(()->changeContent(set2));
        CompletableFuture.allOf(task1, task2).join();
        return ReturnT.SUCCESS;
    }

    private void changeCommentsNumAndThumbsNumInMongo(Set<String> set){
        if(CollectionUtils.isNotEmpty(set)) {
            set.parallelStream().forEach(
                    id -> {
                        List<Integer> integers = articleCache.getCommentsNumAndThumbsNum(id);
                        if (integers != null && integers.size() > 1) {
                            int commentsNum = integers.get(0) == null ? 0 : integers.get(0);
                            int thumbsNum = integers.get(1) == null ? 0 : integers.get(1);
                            int seq = articleCache.getCurrentNickNameSeq(id);
                            Set<Long> users = articleCache.getThumbUserIds(id);
                            Query query = Query.query(Criteria.where(BaseMongoEntity.Fields.id).is(id));
                            Update update = Update.update(Article.Fields.nickNameSequence, seq)
                                    .set(Article.Fields.commentsNum, commentsNum)
                                    .set(Article.Fields.thumbingNum, thumbsNum)
                                    .set(Article.Fields.thumbUserIds, users);
                            mongoTemplate.updateFirst(query, update, Article.class);
                        }
                    }
            );
        }
    }

    private void changeContent(Set<String> set){
        if(CollectionUtils.isNotEmpty(set)) {
            set.parallelStream().forEach(
                    id -> {
                        String content = articleCache.getContent(id);
                        if (StringUtils.isNotBlank(content)) {
                            String abstracts = content.substring(0, 100);
                            CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
                                Query query = Query.query(Criteria.where(BaseMongoEntity.Fields.id).is(id));
                                Update update = Update.update(Article.Fields.abstracts, abstracts);
                                mongoTemplate.updateFirst(query, update, Article.class);
                            });
                            CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
                                Query query = Query.query(Criteria.where(ArticleContent.Fields.articleId).is(id));
                                Update update = Update.update(ArticleContent.Fields.content, content);
                                mongoTemplate.updateFirst(query, update, ArticleContent.class);
                            });
                            CompletableFuture.allOf(task1, task2).join();
                        }
                    }
            );
        }
    }
}
