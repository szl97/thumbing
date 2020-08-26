package com.thumbing.shared.repository.mongo.content;

import com.thumbing.shared.entity.mongo.content.Moments;
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
public interface IMomentsRepository extends IBaseMongoRepository<Moments> {

    @Modifying
    @Query(value = "update moments set content = :content where id = :id")
    void updateContentById(@Param("id") String id, @Param("content") String content);

    @Modifying
    @Query(value = "update moments set nick_name_sequence = :nickNameSequence, comments_num = :commentsNum, thumbing_num = :thumbingNum, thumb_user_ids = :thumbUserIds where id = :id")
    void updateNickUserSequenceAndCommentsNumAndThumbingNumAndThumbUserIdsById
            (@Param("id") String id,
             @Param("nickNameSequence") int nickNameSequence,
             @Param("commentsNum") int commentsNum,
             @Param("thumbingNum") int thumbingNum,
             @Param("thumbUserIds") Set<Long> thumbUserIds);

    Optional<Moments> findByIdAndIsDelete(String id, int isDelete);

    Page<Moments> findAllByIsDelete(int isDelete, Pageable pageable);

    @Modifying
    @Query(value = "update moments set is_delete = 1 where id = :id")
    void updateIsDeleteById(@Param("id") String id);
}
