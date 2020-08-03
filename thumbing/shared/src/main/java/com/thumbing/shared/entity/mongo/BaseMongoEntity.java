package com.thumbing.shared.entity.mongo;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

/**
 * @author Stan Sai
 * @date 2020-07-05
 */

@Getter
@Setter
@FieldNameConstants
public class BaseMongoEntity implements Serializable {
    private String id;
}
