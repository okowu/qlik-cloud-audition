package com.cloud.audition.qlikcloudaudition.message;

import com.cloud.audition.qlikcloudaudition.message.dto.CreateMessageRequestDto;
import com.cloud.audition.qlikcloudaudition.message.dto.MessageDto;
import com.cloud.audition.qlikcloudaudition.message.dto.UpdateMessageRequestDto;
import com.cloud.audition.qlikcloudaudition.message.exception.MessageNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/messages")
@Tag(name = "Message API")
@RequiredArgsConstructor
public class MessageController {

  private final MessageService messageService;

  @PostMapping("")
  @Operation(summary = "Create a message")
  @ApiResponse(responseCode = "201", description = "Message successfully created")
  @ApiResponse(responseCode = "400", description = "Bad Request | Invalid text message supplied")
  @ApiResponse(responseCode = "500", description = "Internal Server Error")
  public ResponseEntity<MessageDto> createMessage(
      @Valid @RequestBody final CreateMessageRequestDto createMessageRequest) {
    log.info("Request to create message (text={})", createMessageRequest);
    final var messageDto = messageService.createMessage(createMessageRequest);
    return ResponseEntity.created(URI.create(messageDto.id().toString())).body(messageDto);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Retrieve a message by a given ID")
  @ApiResponse(responseCode = "200", description = "Message successfully retrieved")
  @ApiResponse(
      responseCode = "400",
      description = "Bad Request | Invalid ID supplied (Must be UUID format)")
  @ApiResponse(responseCode = "404", description = "Not Found")
  @ApiResponse(responseCode = "500", description = "Internal Server Error")
  public ResponseEntity<MessageDto> getMessageById(@Valid @PathVariable final UUID id) {
    try {
      log.info("Request to get message (id={})", id);
      return ResponseEntity.ok(messageService.getMessage(id));
    } catch (final MessageNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update the text value of a message")
  @ApiResponse(responseCode = "200", description = "Message successfully updated")
  @ApiResponse(
      responseCode = "400",
      description =
          "Bad Request | Invalid ID supplied (Must be UUID format) | Invalid text message supplied")
  @ApiResponse(responseCode = "404", description = "Not Found")
  @ApiResponse(responseCode = "500", description = "Internal Server Error")
  public ResponseEntity<MessageDto> updateMessage(
      @Valid @PathVariable final UUID id,
      @Valid @RequestBody final UpdateMessageRequestDto updateMessageRequest) {
    try {
      log.info("Request to update message (id={}, newValue={})", id, updateMessageRequest);
      return ResponseEntity.ok(messageService.updateMessage(id, updateMessageRequest));
    } catch (final MessageNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a message")
  @ApiResponse(responseCode = "200", description = "Message successfully deleted")
  @ApiResponse(
      responseCode = "400",
      description = "Bad Request | Invalid ID supplied (Must be UUID format)")
  @ApiResponse(responseCode = "404", description = "Not Found")
  @ApiResponse(responseCode = "500", description = "Internal Server Error")
  public ResponseEntity<Void> deleteMessage(@Valid @PathVariable final UUID id) {
    try {
      log.info("Request to delete message (id={})", id);
      messageService.deleteMessage(id);
      return ResponseEntity.ok().build();
    } catch (final MessageNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("")
  @Operation(summary = "List all message")
  @ApiResponse(responseCode = "200", description = "")
  @ApiResponse(responseCode = "500", description = "Internal Server Error")
  public ResponseEntity<List<MessageDto>> getMessages() {
    log.info("Request to list messages");
    return ResponseEntity.ok(messageService.getMessages());
  }
}
