package com.thumbing.contentserver.dto.input;

import lombok.Data;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/26 10:10
 */
@Data
public class UpdateMomentsInput extends MomentsIdInput {
    private String content;
}
