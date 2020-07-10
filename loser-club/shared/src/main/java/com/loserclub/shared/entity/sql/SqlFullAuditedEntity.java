package com.loserclub.shared.entity.sql;

import com.loserclub.shared.constants.EntityConstants;
import com.loserclub.shared.jpa.LogicDelete;
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
public class SqlFullAuditedEntity extends SqlEditionEntity {
    private static final long serialVersionUID = -8674477960395112975L;

    @LogicDelete
    @Column(name = EntityConstants.DR)
    private int dr;
}
