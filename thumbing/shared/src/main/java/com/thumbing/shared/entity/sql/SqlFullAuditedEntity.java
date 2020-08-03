package com.thumbing.shared.entity.sql;

import com.thumbing.shared.constants.EntityConstants;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

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

    @Column(name = EntityConstants.IS_DELETE)
    private int is_delete;
}
