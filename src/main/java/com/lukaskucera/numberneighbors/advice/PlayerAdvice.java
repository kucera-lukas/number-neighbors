package com.lukaskucera.numberneighbors.advice;

import com.lukaskucera.numberneighbors.exception.PlayerNameAlreadyExistsException;
import com.lukaskucera.numberneighbors.exception.PlayerNotFoundException;
import com.lukaskucera.numberneighbors.exception.PlayerNumbersPopulatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class PlayerAdvice extends ResponseEntityExceptionHandler {

  @ResponseBody
  @ExceptionHandler(PlayerNameAlreadyExistsException.class)
  ResponseEntity<String> handlePlayerNameAlreadyExists(
    PlayerNameAlreadyExistsException ex
  ) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(PlayerNotFoundException.class)
  ResponseEntity<String> handlePlayerNotFound(PlayerNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(PlayerNumbersPopulatedException.class)
  ResponseEntity<String> handlePlayerNumbersPopulated(
    PlayerNotFoundException ex
  ) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }
}
