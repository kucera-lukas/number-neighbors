package com.lukaskucera.numberneighbors.advice;

import com.lukaskucera.numberneighbors.exception.ResponseAlreadyExistsException;
import com.lukaskucera.numberneighbors.exception.ResponseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ResponseAdvice {

  @ResponseBody
  @ExceptionHandler(ResponseNotFoundException.class)
  ResponseEntity<String> handleResponseNotFound(ResponseNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(ResponseAlreadyExistsException.class)
  ResponseEntity<String> handleResponseAlreadyExists(
    ResponseAlreadyExistsException ex
  ) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }
}
