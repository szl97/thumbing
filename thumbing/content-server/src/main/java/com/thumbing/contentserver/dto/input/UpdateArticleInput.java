package com.thumbing.contentserver.dto.input;

import lombok.Data;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/24 16:37
 */
@Data
public class UpdateArticleInput extends ArticleIdInput {
    private String content;
}
