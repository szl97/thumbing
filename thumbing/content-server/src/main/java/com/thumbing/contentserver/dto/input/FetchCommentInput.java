package com.thumbing.contentserver.dto.input;

import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Stan Sai
 * @date 2020-08-26 4:42
 */
@Data
public class FetchCommentInput implements Serializable {
    private String contentId;
    private ContentType contentType;
}
