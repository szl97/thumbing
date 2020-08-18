package com.thumbing.gateway.swagger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Stan Sai
 * @date 2020-07-10
 */
@Component
@Primary
@Slf4j
public class SwaggerProvider implements SwaggerResourcesProvider {
    private static final String API_URI = "/v2/api-docs";
    @Autowired
    private GatewayProperties gatewayProperties;

    @Override
    public List<SwaggerResource> get() {
        List<RouteDefinition> routes = gatewayProperties.getRoutes();
        return routes.stream().flatMap(routeDefinition -> routeDefinition.getPredicates().stream()
                .filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
                .map(predicateDefinition ->
                        swaggerResource(routeDefinition.getId(), predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("/**", API_URI))
                )).sorted(Comparator.comparing(SwaggerResource::getName))
                .collect(Collectors.toList());
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
