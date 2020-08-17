package com.thumbing.shared.dto.output;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.utils.serializer.LongToStringListSerializer;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/17 16:28
 */
@Data
public class PageResultDto<T> implements Serializable {
    @JsonSerialize(using = LongToStringListSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long totalCount;
    private List<T> items;
    private Integer position;

    public PageResultDto(long totalCount, List<T> items){
        this.totalCount = totalCount;
        this.items = items;
    }

    public PageResultDto(long totalCount, List<T> items, Integer position){
        this.totalCount = totalCount;
        this.items = items;
        this.position = position;
    }
}