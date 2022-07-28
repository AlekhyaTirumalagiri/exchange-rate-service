package com.challenge.exchangeratechallenge.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;
import static com.google.common.base.Predicates.or;

import java.util.Collections;
/**
 * Created By Alekhya Tirumalagiri
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
                .apiInfo(apiInfo()).select().paths(postPaths()).build();
    }

    private Predicate<String> postPaths() {
        return or(regex("/api/posts.*"), regex("/exchange/.*"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Exchange Rate API")
                .description("Exchange Rate API reference")
                .termsOfServiceUrl("http://JustForExample.com")
                .contact("Alekhya Tirumalagiri").license("Alekhya License")
                .licenseUrl("example@example.com").version("1.0").build();
    }
}
