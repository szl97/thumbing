package com.thumbing.contentserver.lockoperation;

import com.thumbing.contentserver.cache.ArticleCache;
import com.thumbing.shared.repository.mongo.content.IArticleContentRepository;
import com.thumbing.shared.repository.mongo.content.IArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/24 18:00
 */
@Component
public class ArticleLockOperation {
    @Autowired
    IArticleRepository articleRepository;
    @Autowired
    IArticleContentRepository contentRepository;
    @Autowired
    ArticleCache articleCache;
}
