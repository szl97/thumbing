package com.thumbing.contentserver.elasticsearch;

import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Stan Sai
 * @date 2020-07-03
 */
@Data
@NoArgsConstructor
public class ElasticBaseEntity {
    private String contentId;
    private Long userId;
    private ContentType name;
    private String title;
    private String content;
    private String tags;
    private LocalDateTime dateTime;
}
