package com.thumbing.shared.swagger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/3 16:57
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class DefaultOperationBuilderPlugin implements OperationBuilderPlugin {
    @Override
    public void apply(OperationContext operationContext) {
        operationContext.operationBuilder().codegenMethodNameStem(operationContext.getName());
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
