package com.loserclub.shared.entity;

import com.loserclub.shared.utils.entity.*;
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
 * @date 2020-06-28
 * 统一主键格式，所有需要主键的表都必须集成，生成的数据模型也必须包含
 */
@EntityListeners(AutoFillEntityListener.class)
@MappedSuperclass
@Getter
@Setter
@FieldNameConstants
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -7455319085169527969L;
    @Id
    @GenericGenerator(name = "snowflake-id", strategy = "assigned")
    @GeneratedValue(generator = "snowflake-id")
    private Long id;
}