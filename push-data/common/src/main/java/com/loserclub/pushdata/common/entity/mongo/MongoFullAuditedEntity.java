package com.loserclub.pushdata.common.entity.mongo;

import com.loserclub.pushdata.common.constants.EntityConstants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
/**
 * @author Stan Sai
 * @date 2020-07-05
 */
@Getter
@Setter
@MappedSuperclass
public class MongoFullAuditedEntity extends MongoCreationEntity {
    private static final long serialVersionUID = -8674477960395112975L;

    @Column(name = EntityConstants.LAST_MODIFY_ID)
    private Long lastModifyId;

    @Column(name = EntityConstants.LAST_MODIFY_TIME)
    private LocalDateTime lastModifyTime;

    @Column(name = EntityConstants.DR)
    private int dr;
}
