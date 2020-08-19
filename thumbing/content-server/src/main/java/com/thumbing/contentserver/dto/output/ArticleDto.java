package com.thumbing.contentserver.dto.output;

import com.thumbing.shared.dto.output.DocumentDto;
import lombok.Data;

/**
 * @author Stan Sai
 * @date 2020-08-19 9:40
 */
@Data
public class ArticleDto extends DocumentDto {
    private String id;
    private String abstracts;
}
