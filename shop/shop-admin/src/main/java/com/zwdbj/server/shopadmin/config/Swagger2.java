package com.zwdbj.server.shopadmin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        String swaggerBasePackageName = "com.zwdbj.server.shopadmin.controller";
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(AppConfigConstant.SWAGGER_ENABLED)
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerBasePackageName))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("商城后端api接口服务")
                .description("app和前端商城接口服务")
                .termsOfServiceUrl("http://www.zwdbj.com")
                .version("1.0")
                .build();
    }

    /**
     * 配置认证模式
     */
    private List<ApiKey> securitySchemes() {
        return newArrayList(new ApiKey("Authorization", "Authorization", "header"));
    }

    /**
     * 配置认证上下文
     */
    private List<SecurityContext> securityContexts() {
        return newArrayList(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build());
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(new SecurityReference("Authorization", authorizationScopes));
    }
}
