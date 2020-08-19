package com.thumbing.shared.repository.mongo.content;

import com.thumbing.shared.entity.mongo.content.Article;
import com.thumbing.shared.repository.mongo.IBaseMongoRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/19 11:21
 */
public interface IArticleRepository extends IBaseMongoRepository<Article> {
    @Modifying
    @Query(value = "update article set abstracts = :abstracts where id = :id")
    void updateAbstractsById(@Param("id") String id, @Param("abstracts") String abstracts);

    @Modifying
    @Query(value = "update article set thumbing_num = :thumbingNum where id = :id")
    void updateThumbingNumById(@Param("id") String id, @Param("thumbingNum") int thumbingNum);

    @Modifying
    @Query(value = "update article set comments_num = :commentsNum where id = :id")
    void updateCommentsNumById(@Param("id") String id, @Param("commentsNum") int commentsNum);

    @Modifying
    @Query(value = "update article set comments_num = :commentsNum, thumbing_num = :thumbingNum where id = :id")
    void updateCommentsNumAndThumbingNumById(@Param("id") String id, @Param("commentsNum") int commentsNum, @Param("thumbingNum") int thumbingNum);
}
