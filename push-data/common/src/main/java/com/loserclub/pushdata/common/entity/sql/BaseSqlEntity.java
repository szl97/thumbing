package com.loserclub.pushdata.common.entity.sql;

import com.loserclub.pushdata.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.MappedSuperclass;
/**
 * @author Stan Sai
 * @date 2020-07-05
 */
@MappedSuperclass
@Getter
@Setter
@FieldNameConstants
public class BaseSqlEntity extends BaseEntity {
}
