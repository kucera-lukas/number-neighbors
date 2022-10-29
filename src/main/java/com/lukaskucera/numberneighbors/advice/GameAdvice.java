package com.lukaskucera.numberneighbors.advice;

import com.lukaskucera.numberneighbors.exception.GameMissingPlayersException;
import com.lukaskucera.numberneighbors.exception.GameNotFoundException;
import com.lukaskucera.numberneighbors.exception.GamePlayersNotPickedNumbersException;
import com.lukaskucera.numberneighbors.exception.GamePopulatedException;
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
  ResponseEntity<String> handleGameNotFound(GameNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(GamePopulatedException.class)
  ResponseEntity<String> handleGamePopulated(GamePopulatedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(GameMissingPlayersException.class)
  ResponseEntity<String> handleGameMissingPlayers(
    GameMissingPlayersException ex
  ) {
    return ResponseEntity
      .status(HttpStatus.UNPROCESSABLE_ENTITY)
      .body(ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(GamePlayersNotPickedNumbersException.class)
  ResponseEntity<String> handleGamePlayersNotPickedNumbers(
    GamePlayersNotPickedNumbersException ex
  ) {
    return ResponseEntity
      .status(HttpStatus.UNPROCESSABLE_ENTITY)
      .body(ex.getMessage());
  }
}
