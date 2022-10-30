package com.lukaskucera.numberneighbors.advice;

import com.lukaskucera.numberneighbors.exception.NumberOutOfRangeException;
import com.lukaskucera.numberneighbors.exception.NumbersNotDistinctException;
import com.lukaskucera.numberneighbors.exception.NumbersPopulatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class NumberAdvice extends ResponseEntityExceptionHandler {

  @ResponseBody
  @ExceptionHandler(NumberOutOfRangeException.class)
  ResponseEntity<String> handleNumberOutOfRange(NumberOutOfRangeException ex) {
    return ResponseEntity
      .status(HttpStatus.UNPROCESSABLE_ENTITY)
      .body(ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(NumbersNotDistinctException.class)
  ResponseEntity<String> handleNumbersNotDistinct(
    NumbersNotDistinctException ex
  ) {
    return ResponseEntity
      .status(HttpStatus.UNPROCESSABLE_ENTITY)
      .body(ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(NumbersPopulatedException.class)
  ResponseEntity<String> handleNumbersPopulated(NumbersPopulatedException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }
}
