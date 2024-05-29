package com.cloud.audition.qlikcloudaudition;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.general.GeneralAdviceTrait;
import org.zalando.problem.spring.web.advice.http.HttpAdviceTrait;
import org.zalando.problem.spring.web.advice.io.IOAdviceTrait;
import org.zalando.problem.spring.web.advice.routing.RoutingAdviceTrait;
import org.zalando.problem.spring.web.advice.validation.MethodArgumentNotValidAdviceTrait;
import org.zalando.problem.spring.web.advice.validation.ValidationAdviceTrait;

@ControllerAdvice
public class GlobalExceptionHandler
    implements GeneralAdviceTrait,
        HttpAdviceTrait,
        IOAdviceTrait,
        RoutingAdviceTrait,
        ValidationAdviceTrait,
        MethodArgumentNotValidAdviceTrait {

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Problem> handleMethodArgumentTypeMismatchException(
      final MethodArgumentTypeMismatchException ex, final NativeWebRequest webRequest) {
    final var sb = new StringBuilder();
    sb.append("Invalid Value ");
    if (ex.getValue() != null) {
      sb.append("'");
      sb.append(ex.getValue());
      sb.append("' ");
    }
    sb.append("for parameter '");
    sb.append(ex.getName());
    sb.append("'");
    return create(ex, buildProblem(Status.BAD_REQUEST, sb.toString()), webRequest);
  }

  private static Problem buildProblem(final Status status, final String message) {
    return Problem.builder()
        .withStatus(status)
        .withTitle(status.getReasonPhrase())
        .withDetail(message)
        .build();
  }
}
