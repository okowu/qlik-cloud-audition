package com.cloud.audition.qlikcloudaudition.message;

import com.cloud.audition.qlikcloudaudition.message.dto.CreateMessageRequestDto;
import com.cloud.audition.qlikcloudaudition.message.dto.MessageDto;
import com.cloud.audition.qlikcloudaudition.message.dto.UpdateMessageRequestDto;
import java.util.List;
import java.util.UUID;

public interface MessageService {

  MessageDto createMessage(CreateMessageRequestDto request);

  MessageDto getMessage(UUID id);

  MessageDto updateMessage(UUID id, UpdateMessageRequestDto request);

  void deleteMessage(UUID id);

  List<MessageDto> getMessages();
}
