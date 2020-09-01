package com.thumbing.shared.repository.mongo.content;

import com.thumbing.shared.entity.mongo.content.Comment;
import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import com.thumbing.shared.repository.mongo.IBaseMongoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/21 16:19
 */
public interface ICommentRepository extends IBaseMongoRepository<Comment> {
    List<Comment> findAllByContentIdAndContentType(String contentId, ContentType contentType, Sort sort);

    Optional<Comment> findByCommentId(Long commentId);
}
