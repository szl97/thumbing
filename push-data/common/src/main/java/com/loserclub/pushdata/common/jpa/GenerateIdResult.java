package com.loserclub.pushdata.common.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Stan Sai
 * @date 2020-06-28
 */

@AllArgsConstructor
@Data
public class GenerateIdResult {
    private boolean success;
    private Long id;
}
