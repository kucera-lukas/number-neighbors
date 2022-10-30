package com.lukaskucera.numberneighbors.advice;

import com.lukaskucera.numberneighbors.exception.GuestPlayerMissingException;
import com.lukaskucera.numberneighbors.exception.HostPlayerMissingException;
import com.lukaskucera.numberneighbors.exception.PlayerIdMissingInJwtTokenClaimsException;
import com.lukaskucera.numberneighbors.exception.PlayerNameAlreadyExistsException;
import com.lukaskucera.numberneighbors.exception.PlayerNotFoundException;
import com.lukaskucera.numberneighbors.exception.PlayerNotOnTurnException;
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
  @ExceptionHandler(PlayerIdMissingInJwtTokenClaimsException.class)
  ResponseEntity<String> handlePlayerIdMissingInJwtTokenClaims(
    PlayerIdMissingInJwtTokenClaimsException ex
  ) {
    return ResponseEntity
      .status(HttpStatus.UNPROCESSABLE_ENTITY)
      .body(ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(HostPlayerMissingException.class)
  ResponseEntity<String> handleHostPlayerMissing(
    HostPlayerMissingException ex
  ) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(GuestPlayerMissingException.class)
  ResponseEntity<String> handleGuestPlayerMissing(
    GuestPlayerMissingException ex
  ) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(PlayerNotOnTurnException.class)
  ResponseEntity<String> handlePlayerNotOnTurn(PlayerNotOnTurnException ex) {
    return ResponseEntity
      .status(HttpStatus.UNPROCESSABLE_ENTITY)
      .body(ex.getMessage());
  }
}
