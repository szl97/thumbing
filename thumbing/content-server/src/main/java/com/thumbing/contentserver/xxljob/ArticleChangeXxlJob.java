package com.thumbing.contentserver.xxljob;

import cn.hutool.core.util.ArrayUtil;
import com.thumbing.contentserver.cache.ArticleCache;
import com.thumbing.shared.repository.mongo.content.IArticleContentRepository;
import com.thumbing.shared.repository.mongo.content.IArticleRepository;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @XxlJob("articleChangeHandler")
    public ReturnT<String> articleChangeHandler(){
        Set<String> set1 = articleCache.getAndClearThumbsOrCommentsChangedSet();
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> changeCommentsNumAndThumbsNumInMongo(set1));
        Set<String> set2 = articleCache.getAndClearContentChangedSet();
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(()->changeContent(set2));
        CompletableFuture.allOf(task1, task2);
        return ReturnT.SUCCESS;
    }

    private void changeCommentsNumAndThumbsNumInMongo(Set<String> set){
        if(ArrayUtil.isNotEmpty(set)) {
            set.parallelStream().forEach(
                    id -> {
                        List<Integer> integers = articleCache.getCommentsNumAndThumbsNum(id);
                        if (ArrayUtil.isNotEmpty(integers) && integers.size() > 1) {
                            int commentsNum = integers.get(0) == null ? 0 : integers.get(0);
                            int thumbsNum = integers.get(1) == null ? 0 : integers.get(1);
                            articleRepository.updateCommentsNumAndThumbingNumById(id, commentsNum, thumbsNum);
                        }
                    }
            );
        }
    }

    private void changeContent(Set<String> set){
        if(ArrayUtil.isNotEmpty(set)) {
            set.parallelStream().forEach(
                    id -> {
                        String content = articleCache.getContent(id);
                        if (StringUtils.isNotBlank(content)) {
                            String abstracts = content.substring(0, 100);
                            CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> articleRepository.updateAbstractsById(id, abstracts));
                            CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> articleContentRepository.updateContentByArticleId(id, content));
                            CompletableFuture.allOf(task1, task2);
                        }
                    }
            );
        }
    }
}