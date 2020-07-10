package com.loserclub.shared.entity.mongo;

import com.loserclub.shared.constants.EntityConstants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author Stan Sai
 * @date 2020-07-05
 */
@Getter
@Setter
@MappedSuperclass
public class MongoFullAuditedEntity extends MongoEditionEntity {
    private static final long serialVersionUID = -8674477960395112975L;

    @Column(name = EntityConstants.DR)
    private int dr;
}
