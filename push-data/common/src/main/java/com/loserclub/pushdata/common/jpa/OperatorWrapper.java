package com.loserclub.pushdata.common.jpa;

import lombok.Data;

/**
 * @author Stan Sai
 * @date 2020-06-28
 */
@Data
public class OperatorWrapper {
    private SpecificationWrapper<?> specWrapper;
    private String name;
    private Object value;

    public <Y extends Comparable<? super Y>> Y getCompareValue(){
        return (Y)value;
    }
}
