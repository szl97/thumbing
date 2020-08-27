package com.thumbing.shared.repository.mongo.content;

import com.thumbing.shared.entity.mongo.content.Article;
import com.thumbing.shared.repository.mongo.IBaseMongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/19 11:21
 */
public interface IArticleRepository extends IBaseMongoRepository<Article> {
    @Modifying
    @Query(value = "update article set abstracts = :abstracts where id = :id")
    void updateAbstractsById(@Param("id") String id, @Param("abstracts") String abstracts);

    @Modifying
    @Query(value = "update article set nick_name_sequence = :nickNameSequence, comments_num = :commentsNum, thumbing_num = :thumbingNum, thumb_user_ids = :thumbUserIds where id = :id")
    void updateNickUserSequenceAndCommentsNumAndThumbingNumAndThumbUserIdsById
            (@Param("id") String id,
             @Param("nickNameSequence") int nickNameSequence,
             @Param("commentsNum") int commentsNum,
             @Param("thumbingNum") int thumbingNum,
             @Param("thumbUserIds") Set<Long> thumbUserIds);

    Optional<Article> findByIdAndIsDelete(String id, int isDelete);

    Page<Article> findAllByIsDelete(int isDelete, Pageable pageable);

    @Modifying
    @Query(value = "update article set is_delete = 1 where id = :id")
    void updateIsDeleteById(@Param("id") String id);

    Page<Article> findAllByUserIdAndIsDelete(Long userId, int isDelete, Pageable pageable);
}
