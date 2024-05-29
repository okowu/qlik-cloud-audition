package com.cloud.audition.qlikcloudaudition.integration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocumentationConfig {

  @Bean
  public OpenAPI apiDocsConfig() {
    return new OpenAPI()
        .info(
            new Info()
                .title("API Documentation")
                .version("0.0.1")
                .contact(new Contact().name("Oscar KOWU").email("devs.okowu@gmail.com")));
  }
}
