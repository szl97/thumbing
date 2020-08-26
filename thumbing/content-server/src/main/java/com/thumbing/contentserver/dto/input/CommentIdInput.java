package com.thumbing.contentserver.dto.input;

import com.thumbing.shared.dto.EntityDto;
import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import lombok.Data;

/**
 * @author Stan Sai
 * @date 2020-08-26 5:00
 */
@Data
public class CommentIdInput extends EntityDto {
    private String contentId;
    private ContentType contentType;
}
