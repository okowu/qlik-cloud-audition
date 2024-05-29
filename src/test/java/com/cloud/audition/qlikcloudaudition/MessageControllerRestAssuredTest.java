package com.cloud.audition.qlikcloudaudition;

import static org.hamcrest.Matchers.*;

import com.cloud.audition.qlikcloudaudition.db.message.Message;
import com.cloud.audition.qlikcloudaudition.db.message.MessageRepository;
import com.cloud.audition.qlikcloudaudition.message.dto.CreateMessageRequestDto;
import com.cloud.audition.qlikcloudaudition.message.dto.UpdateMessageRequestDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(
    classes = QlikCloudAuditionApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageControllerRestAssuredTest {

  @LocalServerPort private Integer port;

  private static final PostgreSQLContainer<?> postgres =
      new PostgreSQLContainer<>("postgres:16-alpine");

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @BeforeAll
  static void beforeAll() {
    postgres.start();
  }

  @AfterAll
  static void afterAll() {
    postgres.stop();
  }

  @Autowired private MessageRepository messageRepository;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost:" + port;
    messageRepository.deleteAll();
  }

  @Test
  void postMessage_shouldReturn_HttpStatusCreated_withCreatedMessage() {
    var text = "racecar";
    var request = new CreateMessageRequestDto(text);

    RestAssured.given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body(request)
        .when()
        .post("/v1/messages")
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .body("text", equalTo(text))
        .body("isPalindrome", equalTo(true));
  }

  @Test
  void postMessage_shouldReturn_HttpStatusBadRequest_givenNonValidTextMessage() {
    var request = new CreateMessageRequestDto("545");

    RestAssured.given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body(request)
        .when()
        .post("/v1/messages")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("violations.field", hasItems("text"))
        .body("violations.message", hasItems("must match \"^[A-Za-z]*$\""));
  }

  @Test
  void updateMessage_shouldReturn_HttpStatusOk_withUpdatedMessage() {
    var message = new Message();
    message.setValue("madam");
    message.setPalindrome(true);
    message = messageRepository.save(message);

    var newText = "popo";
    var request = new UpdateMessageRequestDto(newText);

    RestAssured.given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body(request)
        .when()
        .put(String.format("/v1/messages/%s", message.getId()))
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("id", equalTo(message.getId().toString()))
        .body("text", equalTo(newText))
        .body("isPalindrome", equalTo(false));
  }

  @Test
  void updateMessage_shouldReturn_HttpStatusBadRequest_givenInvalidId() {
    var request = new UpdateMessageRequestDto("sometext");

    var id = "dghjdhdfkj";

    RestAssured.given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body(request)
        .when()
        .delete(String.format("/v1/messages/%s", id))
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("detail", equalTo(String.format("Invalid Value '%s' for parameter 'id'", id)));
  }

  @Test
  void updateMessage_shouldReturn_HttpStatusBadRequest_givenNonValidTextMessage() {
    var id = UUID.randomUUID();
    var request = new UpdateMessageRequestDto("545");

    RestAssured.given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body(request)
        .when()
        .put(String.format("/v1/messages/%s", id))
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("violations.field", hasItems("text"))
        .body("violations.message", hasItems("must match \"^[A-Za-z]*$\""));
  }

  @Test
  void deleteMessage_shouldReturn_HttpStatusOk_withUpdatedMessage() {
    var message = new Message();
    message.setValue("madam");
    message.setPalindrome(true);
    message = messageRepository.save(message);

    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .delete(String.format("/v1/messages/%s", message.getId()))
        .then()
        .statusCode(HttpStatus.OK.value());
  }

  @Test
  void deleteMessage_shouldReturn_HttpStatusBadRequest_givenInvalidId() {
    var id = 458;

    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .delete(String.format("/v1/messages/%s", id))
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("detail", equalTo(String.format("Invalid Value '%s' for parameter 'id'", id)));
  }

  @Test
  void getMessages_shouldReturn_HttpStatusOk_withListOfMessages() {
    var message1 = new Message();
    message1.setValue("madam");
    message1.setPalindrome(true);
    message1 = messageRepository.save(message1);

    var message2 = new Message();
    message2.setValue("pop");
    message2.setPalindrome(false);
    message2 = messageRepository.save(message2);

    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("/v1/messages")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("$", hasSize(2))
        .body("id", hasItems(message1.getId().toString(), message2.getId().toString()))
        .body("text", hasItems(message1.getValue(), message2.getValue()))
        .body("isPalindrome", hasItems(true, false));
  }
}
