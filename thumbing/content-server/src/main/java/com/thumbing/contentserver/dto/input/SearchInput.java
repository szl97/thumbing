package com.thumbing.contentserver.dto.input;

import com.thumbing.shared.dto.input.PagedAndSortedInput;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/27 10:14
 */
@Data
public class SearchInput extends PagedAndSortedInput {
    @ApiModelProperty(value = "关键字")
    @NotNull(message = "关键字不可为空")
    @Size(max = 30, message = "搜索内容长度限制1-30")
    private String keyword;
}
