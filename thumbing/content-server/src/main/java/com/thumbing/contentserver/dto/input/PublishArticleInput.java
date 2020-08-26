package com.thumbing.contentserver.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author Stan Sai
 * @date 2020-08-19 9:43
 */
@Data
public class PublishArticleInput implements Serializable {
    @ApiModelProperty(value = "标题")
    @NotNull(message = "标题不可为空")
    @Size(max = 30)
    private String title;
    @ApiModelProperty(value = "标签")
    @NotNull(message = "标签不可为空")
    private Set<String> tagIds;
    @ApiModelProperty(value = "内容")
    @NotNull(message = "内容不可为空")
    @Size(max = 10000, min = 500)
    private String content;
    @ApiModelProperty(value = "图片在对象服务器上的存储Id")
    private List<String> graphIds;
}
