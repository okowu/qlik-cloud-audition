package com.cloud.audition.qlikcloudaudition.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
public class ObjectMapperConfig {

  @Bean
  public ObjectMapper objectMapper() {
    final var objectMapper = new ObjectMapper();
    objectMapper.registerModules(new ProblemModule(), new ConstraintViolationProblemModule());
    return objectMapper;
  }

  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(
      final ObjectMapper objectMapper) {
    return new MappingJackson2HttpMessageConverter(objectMapper);
  }
}
