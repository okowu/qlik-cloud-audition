package com.cloud.audition.qlikcloudaudition;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cloud.audition.qlikcloudaudition.message.MessageController;
import com.cloud.audition.qlikcloudaudition.message.MessageService;
import com.cloud.audition.qlikcloudaudition.message.dto.CreateMessageRequestDto;
import com.cloud.audition.qlikcloudaudition.message.dto.MessageDto;
import com.cloud.audition.qlikcloudaudition.message.dto.UpdateMessageRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MessageController.class)
public class MessageControllerMockMvcTest {

  @Autowired MockMvc mockMvc;

  @Autowired ObjectMapper objectMapper;

  @MockBean MessageService messageService;

  @Test
  void postMessage_shouldReturn_HttpStatusCreated_withCreatedMessage() throws Exception {

    var request = new CreateMessageRequestDto("sometext");
    var message = new MessageDto(UUID.randomUUID(), "sometext", true);

    given(messageService.createMessage(request)).willReturn(message);

    mockMvc
        .perform(
            post("/v1/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(message.id().toString())))
        .andExpect(jsonPath("$.text", is("sometext")))
        .andExpect(jsonPath("$.isPalindrome", is(true)));
  }

  @Test
  void postMessage_shouldReturn_HttpStatusBadRequest_givenNonValidTextMessage() throws Exception {
    var request = new CreateMessageRequestDto("545");

    mockMvc
        .perform(
            post("/v1/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.violations[0].field", is("text")))
        .andExpect(jsonPath("$.violations[0].message", is("must match \"^[A-Za-z]*$\"")));
  }

  @Test
  void updateMessage_shouldReturn_HttpStatusOk_withUpdatedMessage() throws Exception {
    var id = UUID.randomUUID();
    var request = new UpdateMessageRequestDto("updatedtext");
    var message = new MessageDto(id, "updatedtext", false);

    given(messageService.updateMessage(id, request)).willReturn(message);

    mockMvc
        .perform(
            put(String.format("/v1/messages/%s", id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(id.toString())))
        .andExpect(jsonPath("$.text", is("updatedtext")))
        .andExpect(jsonPath("$.isPalindrome", is(false)));
  }

  @Test
  void updateMessage_shouldReturn_HttpStatusBadRequest_givenInvalidId() throws Exception {
    var request = new UpdateMessageRequestDto("some_text");

    mockMvc
        .perform(
            put("/v1/messages/jihjhk8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.detail", is("Invalid Value 'jihjhk8' for parameter 'id'")));
  }

  @Test
  void updateMessage_shouldReturn_HttpStatusBadRequest_givenNonValidTextMessage() throws Exception {
    var id = UUID.randomUUID();
    var request = new UpdateMessageRequestDto("545");

    mockMvc
        .perform(
            put(String.format("/v1/messages/%s", id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.violations[0].field", is("text")))
        .andExpect(jsonPath("$.violations[0].message", is("must match \"^[A-Za-z]*$\"")));
  }

  @Test
  void deleteMessage_shouldReturn_HttpStatusOk_withUpdatedMessage() throws Exception {
    var id = UUID.randomUUID();

    willDoNothing().given(messageService).deleteMessage(id);

    mockMvc
        .perform(
            delete(String.format("/v1/messages/%s", id)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void deleteMessage_shouldReturn_HttpStatusBadRequest_givenInvalidId() throws Exception {
    mockMvc
        .perform(delete("/v1/messages/345").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.detail", is("Invalid Value '345' for parameter 'id'")));
  }

  @Test
  void getMessages_shouldReturn_HttpStatusOk_withListOfMessages() throws Exception {
    var message1 = new MessageDto(UUID.randomUUID(), "madam", true);
    var message2 = new MessageDto(UUID.randomUUID(), "pop", false);
    var messages = List.of(message1, message2);

    given(messageService.getMessages()).willReturn(messages);

    mockMvc
        .perform(get("/v1/messages").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(2)))
        .andExpect(jsonPath("$[0].id", is(message1.id().toString())))
        .andExpect(jsonPath("$[0].text", is("madam")))
        .andExpect(jsonPath("$[0].isPalindrome", is(true)))
        .andExpect(jsonPath("$[1].id", is(message2.id().toString())))
        .andExpect(jsonPath("$[1].text", is("pop")))
        .andExpect(jsonPath("$[1].isPalindrome", is(false)));
  }
}
