package com.thumbing.shared.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.NotNull;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import com.thumbing.shared.utils.serializer.LongToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/3 15:31
 */
@Data
public class EntityDto implements Serializable {
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @NotNull
    private Long id;
}
