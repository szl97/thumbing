package com.loserclub.generateid;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Stan Sai
 * @date 2020-06-28
 */

@AllArgsConstructor
@Data
public class Result {
    private boolean success;
    private Long id;
}
