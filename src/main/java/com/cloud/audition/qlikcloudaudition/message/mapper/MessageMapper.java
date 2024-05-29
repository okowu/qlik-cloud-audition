package com.cloud.audition.qlikcloudaudition.message.mapper;

import com.cloud.audition.qlikcloudaudition.db.message.Message;
import com.cloud.audition.qlikcloudaudition.db.message.MessageAdditionalInfo;
import com.cloud.audition.qlikcloudaudition.message.dto.MessageDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageMapper {

  public static MessageDto mapMessage(final Message message) {

    final MessageAdditionalInfo additionalInfo = message.getAdditionalInfo();

    return MessageDto.builder()
        .id(message.getId())
        .text(message.getValue())
        .isPalindrome(additionalInfo.isPalindrome())
        .build();
  }
}
