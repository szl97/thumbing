package com.thumbing.shared.entity.sql;

import com.thumbing.shared.constants.EntityConstants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

/**
 * @author Stan Sai
 * @date 2020-07-05
 */
@Getter
@Setter
@MappedSuperclass
public class SqlEditionEntity extends SqlCreationEntity {
    private static final long serialVersionUID = -8674477960395112975L;


    @Column(name = EntityConstants.LAST_MODIFY_TIME)
    private LocalDateTime lastModifyTime;

    @Version
    @Column(name = EntityConstants.VERSION)
    private int version;
}
