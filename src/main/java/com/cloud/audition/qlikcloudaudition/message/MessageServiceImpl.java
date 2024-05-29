package com.cloud.audition.qlikcloudaudition.message;

import com.cloud.audition.qlikcloudaudition.db.message.Message;
import com.cloud.audition.qlikcloudaudition.db.message.MessageRepository;
import com.cloud.audition.qlikcloudaudition.message.dto.CreateMessageRequestDto;
import com.cloud.audition.qlikcloudaudition.message.dto.MessageDto;
import com.cloud.audition.qlikcloudaudition.message.dto.UpdateMessageRequestDto;
import com.cloud.audition.qlikcloudaudition.message.exception.MessageNotFoundException;
import com.cloud.audition.qlikcloudaudition.message.mapper.MessageMapper;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

  private final MessageRepository messageRepository;

  @Override
  public MessageDto createMessage(@NotNull final CreateMessageRequestDto request) {
    final var isPalindrome = MessageUtils.isPalindrome(request.text());

    final var message = new Message();
    message.setValue(request.text());
    message.setPalindrome(isPalindrome);
    return MessageMapper.mapMessage(messageRepository.save(message));
  }

  @Override
  public MessageDto getMessage(@NotNull final UUID id) {
    return messageRepository
        .findById(id)
        .map(MessageMapper::mapMessage)
        .orElseThrow(MessageNotFoundException::new);
  }

  @Override
  public MessageDto updateMessage(
      @NotNull final UUID id, @NotNull final UpdateMessageRequestDto request) {

    final var message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);

    if (StringUtils.isBlank(request.text())) {
      return MessageMapper.mapMessage(message);
    }

    message.setValue(request.text());
    message.setPalindrome(MessageUtils.isPalindrome(request.text()));
    return MessageMapper.mapMessage(messageRepository.save(message));
  }

  @Override
  public void deleteMessage(@NotNull final UUID id) {
    final var message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);
    messageRepository.delete(message);
  }

  @Override
  public List<MessageDto> getMessages() {
    return messageRepository.findAll().stream().map(MessageMapper::mapMessage).toList();
  }
}
