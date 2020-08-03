package com.thumbing.shared.swagger;

import com.thumbing.shared.condition.SwaggerCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.OperationNameGenerator;

/**
 * 重写swaggernamegenerator  覆盖默认实现
 * @Author: Stan Sai
 * @Date: 2020/8/3 16:56
 */
@Component
@Primary
@Conditional(SwaggerCondition.class)
public class DefaultOperationNameGenerator implements OperationNameGenerator {
    @Override
    public String startingWith(String s) {
        return s;
    }
}
