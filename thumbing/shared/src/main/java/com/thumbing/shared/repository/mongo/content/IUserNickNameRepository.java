package com.thumbing.shared.repository.mongo.content;

import com.thumbing.shared.entity.mongo.content.UserNickName;
import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import com.thumbing.shared.repository.mongo.IBaseMongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/20 11:05
 */
public interface IUserNickNameRepository extends IBaseMongoRepository<UserNickName> {
    Optional<UserNickName> findByUserIdAndContentIdAndContentType(Long userId, String contentId, ContentType contentType);

    List<UserNickName> findAllByContentIdAndContentType(String contentId, ContentType contentType);
}
