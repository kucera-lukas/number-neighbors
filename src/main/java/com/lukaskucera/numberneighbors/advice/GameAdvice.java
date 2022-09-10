package com.lukaskucera.numberneighbors.advice;

import com.lukaskucera.numberneighbors.exception.GameNotFoundException;
import com.lukaskucera.numberneighbors.exception.GamePopulatedException;
import com.lukaskucera.numberneighbors.exception.GuestPlayerMissingException;
import com.lukaskucera.numberneighbors.exception.HostPlayerMissingException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class GameAdvice extends ResponseEntityExceptionHandler {

  @ResponseBody
  @ExceptionHandler(GameNotFoundException.class)
  ResponseEntity<String> handleGameNotFound(@NotNull GameNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(GamePopulatedException.class)
  ResponseEntity<String> handleGamePopulated(@NotNull GamePopulatedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(HostPlayerMissingException.class)
  ResponseEntity<String> handleHostPlayerMissing(@NotNull HostPlayerMissingException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(GuestPlayerMissingException.class)
  ResponseEntity<String> handleGuestPlayerMissing(@NotNull GuestPlayerMissingException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }
}
