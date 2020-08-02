package com.thumbing.shared.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author Stan Sai
 * @date 2020-08-02 17:24
 */
@Data
public class PagedAndSortedInput {
    /**
     * 排序字段名
     */
    protected String sorting;
    /**
     * 排序方式
     */
    protected Sort.Direction sortingDirection = Sort.Direction.ASC;
    /**
     * 页码
     */
    private int pageNumber = 1;
    /**
     * 每页显示的记录数
     */
    private int pageSize = 10;
    /**
     * 在redis list中的位置
     */
    private Integer position;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Pageable pageable;

    public Pageable getPageable() {
        return PageRequest.of(pageNumber - 1, pageSize, getSort());
    }

    protected Sort getSort() {
        return StringUtils.isBlank(sorting) ? Sort.unsorted() : Sort.by(sortingDirection, sorting);
    }
}
