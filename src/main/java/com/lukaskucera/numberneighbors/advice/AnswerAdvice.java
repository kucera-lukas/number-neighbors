package com.lukaskucera.numberneighbors.advice;

import com.lukaskucera.numberneighbors.exception.AnswerAlreadyExistsException;
import com.lukaskucera.numberneighbors.exception.AnswerRequiresYesException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AnswerAdvice extends ResponseEntityExceptionHandler {

  @ResponseBody
  @ExceptionHandler(AnswerAlreadyExistsException.class)
  ResponseEntity<String> handleAnswerAlreadyExists(
    AnswerAlreadyExistsException ex
  ) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(AnswerRequiresYesException.class)
  ResponseEntity<String> handleAnswerRequiresYes(
    AnswerRequiresYesException ex
  ) {
    return ResponseEntity
      .status(HttpStatus.UNPROCESSABLE_ENTITY)
      .body(ex.getMessage());
  }
}
