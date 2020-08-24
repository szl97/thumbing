package com.thumbing.contentserver.dto.input;

import com.thumbing.shared.dto.output.DocumentDto;
import lombok.Data;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/24 16:35
 */
@Data
public class ThumbArticleInput extends DocumentDto {
    private boolean add;
}
