package com.thumbing.contentserver.service.impl;

import com.thumbing.shared.entity.mongo.content.Article;
import com.thumbing.shared.repository.mongo.content.IArticleRepository;
import com.thumbing.shared.service.impl.BaseMongoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/3 17:20
 */
@Service
@Transactional
public class ArticleService extends BaseMongoService<Article, IArticleRepository> {
}
