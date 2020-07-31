package com.thumbing.shared.swagger;

import com.thumbing.shared.condition.RedisCondition;
import com.thumbing.shared.condition.SecurityCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Stan Sai
 * @date 2020-07-10
 */
@ComponentScan(
        basePackages = "com.thumbing"
)
@Configuration
@EnableSwagger2
@Conditional({
        RedisCondition.class,
        SecurityCondition.class
})
public class SwaggerConfig {

    @Bean
    public Docket api() {

        //移除掉默认的OperationNameGenerator实现

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.thumbing"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList( apiKeys()))
                .securityContexts(Collections.singletonList(securityContext()));
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger API")
                .description("swagger")
                .termsOfServiceUrl("")
                .contact(new Contact("Stan Sai", "", ""))
                .version("2.0")
                .build();
    }
    private ApiKey[] apiKeys() {
        return new ApiKey[]{ new ApiKey("JWT (Bearer $token)", "Authorization", "header")};
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        List<SecurityReference> references=new ArrayList<>();
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[0];
        references.add(new SecurityReference("JWT (Bearer $token)", authorizationScopes));
        return  references;
    }
}

