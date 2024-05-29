package com.cloud.audition.qlikcloudaudition.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

@Builder
@Schema(description = "Message Response")
public record MessageDto(@NotNull UUID id, @NotBlank String text, @NotNull boolean isPalindrome) {}
