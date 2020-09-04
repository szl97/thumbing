package com.thumbing.contentserver.dto.input;

import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Stan Sai
 * @date 2020-08-26 4:42
 */
@Data
public class FetchCommentInput implements Serializable {
    @NotNull(message = "文章或帖子Id不可为空")
    private String contentId;
    @NotNull(message = "内容类型不可为空")
    private ContentType contentType;
}
