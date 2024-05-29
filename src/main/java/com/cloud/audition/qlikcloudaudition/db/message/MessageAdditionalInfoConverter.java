package com.cloud.audition.qlikcloudaudition.db.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class MessageAdditionalInfoConverter
    implements AttributeConverter<MessageAdditionalInfo, String> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(final MessageAdditionalInfo messageAdditionalInfo) {
    try {
      return objectMapper.writeValueAsString(messageAdditionalInfo);
    } catch (final JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public MessageAdditionalInfo convertToEntityAttribute(final String dbData) {
    if (StringUtils.isBlank(dbData)) {
      return null;
    }

    try {
      return objectMapper.readValue(dbData, MessageAdditionalInfo.class);
    } catch (final JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
