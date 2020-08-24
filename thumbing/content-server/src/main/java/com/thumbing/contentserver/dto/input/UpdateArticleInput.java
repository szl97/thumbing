package com.thumbing.contentserver.dto.input;

import com.thumbing.shared.dto.output.DocumentDto;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/24 16:37
 */
@Data
public class UpdateArticleInput extends DocumentDto {
    private String title;
    private Set<Long> tagIds;
    private String content;
    private List<String> graphIds;
}
