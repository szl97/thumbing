package com.thumbing.shared.entity.sql;

import com.thumbing.shared.utils.entity.AutoFillEntityListener;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author Stan Sai
 * @date 2020-07-05
 */
@MappedSuperclass
@EntityListeners(AutoFillEntityListener.class)
@Getter
@Setter
@FieldNameConstants
public class BaseSqlEntity implements Serializable {
    private static final long serialVersionUID = -7455319085169527969L;
    @Id
    @GenericGenerator(name = "snowflake-id", strategy = "assigned")
    @GeneratedValue(generator = "snowflake-id")
    private Long id;
}
