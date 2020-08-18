package com.thumbing.contentserver.elasticsearch;

import com.thumbing.contentserver.dto.enums.ContentType;
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
    private final String index = "doc";
    private Long id;
    private Long userId;
    private ContentType name;
    private String title;
    private String content;
    private String contentId;
    private String tags;
    private LocalDateTime dateTime;
    private String nickName;
}
