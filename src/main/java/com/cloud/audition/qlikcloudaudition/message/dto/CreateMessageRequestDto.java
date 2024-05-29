package com.cloud.audition.qlikcloudaudition.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Message Creation Request Payload")
public record CreateMessageRequestDto(
    @Valid @NotBlank @Pattern(regexp = "^[A-Za-z]*$") String text) {
  @Override
  public String toString() {
    return text;
  }
}
