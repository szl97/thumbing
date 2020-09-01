package com.thumbing.shared.entity.mongo;

import com.thumbing.shared.constants.EntityConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author Stan Sai
 * @date 2020-07-05
 */
@Getter
@Setter
@FieldNameConstants
@MappedSuperclass
public class MongoFullAuditedEntity extends MongoEditionEntity {
    private static final long serialVersionUID = -8674477960395112975L;
    private int isDelete;
}
