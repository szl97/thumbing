package com.thumbing.contentserver.dto.input;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/26 14:25
 */
@Data
public class PublishMomentsInput implements Serializable {
    private String title;
    private Set<Long> tagIds;
    private String content;
    private List<String> graphIds;
}
