package com.thumbing.shared.entity.mongo;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author Stan Sai
 * @date 2020-07-05
 */
@MappedSuperclass
@Getter
@Setter
@FieldNameConstants
public class MongoCreationEntity extends BaseMongoEntity {
    private static final long serialVersionUID = -305433542418558052L;
    private LocalDateTime createTime;
}
