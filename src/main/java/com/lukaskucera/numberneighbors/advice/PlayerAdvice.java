package com.lukaskucera.numberneighbors.advice;

import com.lukaskucera.numberneighbors.exception.PlayerNameAlreadyExistsException;
import com.lukaskucera.numberneighbors.exception.PlayerNotFoundException;
import org.jetbrains.annotations.NotNull;
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
    @NotNull PlayerNameAlreadyExistsException ex
  ) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(PlayerNotFoundException.class)
  ResponseEntity<String> handlePlayerNotFound(
    @NotNull PlayerNotFoundException ex
  ) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }
}
