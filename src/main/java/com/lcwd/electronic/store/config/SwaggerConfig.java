package com.lcwd.electronic.store.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.apache.http.protocol.HTTP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@SecurityScheme(
        name = "scheme1",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@OpenAPIDefinition(
        servers = {
                @Server(url = "/", description = "Default Server URL")
        }
        ,
        info = @Info(
                title = "Electronic Store API's",
                description = "This is Electronic Store Project API Developed By Pranay Mate",
                version = "1.0",
                contact = @Contact(
                        name = "Pranay Mate",
                        email="pranaymate0706@gmail.com",
                        url="https://www.linkedin.com/in/pranay-mate-895620250/"
                ),
                license = @License(
                        name = "OPEN LICENCE",
                        url="https://www.linkedin.com/in/pranay-mate-895620250/"
                )
        ),
                externalDocs = @ExternalDocumentation(
                        description =  "This is External Docs",
                        url = "https://www.linkedin.com/in/pranay-mate-895620250/")
)
public class SwaggerConfig {



 /*   @Bean
    public OpenAPI openAPI()
    {
        String schemeName="bearerScheme";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(schemeName)
                )
                .components(new Components()
                        .addSecuritySchemes(schemeName,new SecurityScheme()
                                .name(schemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("bearer")
                        )
                )
                .info(new Info().title("Electronic Store API's")
                        .description("This is Electronic Store Project API Developed By Pranay Mate")
                        .version("1.0")
                        .contact(new Contact().name("Pranay Mate").email("pranaymate0706@gmail.com").url("https://www.linkedin.com/in/pranay-mate-895620250/"))
                        .license(new License().name("Apache")))
                .externalDocs(new ExternalDocumentation().url("https://www.linkedin.com/in/pranay-mate-895620250/")
                        .description("This is External Url"));
    }*/

    /*@Bean
    public Docket docket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(getApiInfo());


        docket.securityContexts(Arrays.asList(getSecurityContext()));
        docket.securitySchemes(Arrays.asList(getSchemes()));

        ApiSelectorBuilder select = docket.select();
        select.apis(RequestHandlerSelectors.any());
        select.paths(PathSelectors.any());
        Docket build = select.build();
        return build;
    }


    private SecurityContext getSecurityContext() {

        SecurityContext context = SecurityContext
                .builder()
                .securityReferences(getSecurityReferences())
                .build();
        return context;
    }

    private List<SecurityReference> getSecurityReferences() {
        AuthorizationScope[] scopes = {new AuthorizationScope("Global", "Access Every Thing")};
        return Arrays.asList(new SecurityReference("JWT", scopes));

    }

    private ApiKey getSchemes() {
        return new ApiKey("JWT", "Authorization", "header");
    }


    private ApiInfo getApiInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Electronic Store Backend : APIS ",
                "This is backend project created by Pranay Mate",
                "1.0.0V",
                "https://www.linkedin.com/in/pranay-mate-895620250/",
                new Contact("Pranay Mate", "https://www.linkedin.com/in/pranay-mate-895620250/", "pranaymate0706@gmail.com"),
                "License of APIS",
                "https://www.linkedin.com/in/pranay-mate-895620250/",
                new ArrayDeque<>()
        );

        return apiInfo;

    }*/


}
