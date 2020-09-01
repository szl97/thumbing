package com.thumbing.shared.repository.mongo.content;

import com.thumbing.shared.entity.mongo.content.Article;
import com.thumbing.shared.repository.mongo.IBaseMongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/19 11:21
 */
public interface IArticleRepository extends IBaseMongoRepository<Article> {

    Optional<Article> findByIdAndIsDelete(String id, int isDelete);

    Page<Article> findAllByIsDelete(int isDelete, Pageable pageable);

    Page<Article> findAllByUserIdAndIsDelete(Long userId, int isDelete, Pageable pageable);
}
