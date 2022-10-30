package com.lukaskucera.numberneighbors.advice;

import com.lukaskucera.numberneighbors.exception.TurnNotCompletedException;
import com.lukaskucera.numberneighbors.exception.TurnRequiresAvailableNumberException;
import com.lukaskucera.numberneighbors.exception.TurnRequiresChosenNumberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class TurnAdvice {

  @ResponseBody
  @ExceptionHandler(TurnNotCompletedException.class)
  ResponseEntity<String> handleTurnNotCompleted(TurnNotCompletedException ex) {
    return ResponseEntity
      .status(HttpStatus.UNPROCESSABLE_ENTITY)
      .body(ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(TurnRequiresChosenNumberException.class)
  ResponseEntity<String> handleTurnRequiresChosenNumber(
    TurnRequiresChosenNumberException ex
  ) {
    return ResponseEntity
      .status(HttpStatus.UNPROCESSABLE_ENTITY)
      .body(ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(TurnRequiresAvailableNumberException.class)
  ResponseEntity<String> handleTurnRequiresAvailableNumber(
    TurnRequiresAvailableNumberException ex
  ) {
    return ResponseEntity
      .status(HttpStatus.UNPROCESSABLE_ENTITY)
      .body(ex.getMessage());
  }
}
