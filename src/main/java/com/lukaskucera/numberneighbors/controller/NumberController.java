package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.dto.AuthDTO;
import com.lukaskucera.numberneighbors.dto.NumberDTO;
import com.lukaskucera.numberneighbors.request.NewNumbersRequest;
import com.lukaskucera.numberneighbors.service.NumberServiceImpl;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberController {

  private static final Logger logger = LoggerFactory.getLogger(
    NumberController.class
  );

  private final NumberServiceImpl numberService;

  public NumberController(NumberServiceImpl numberService) {
    this.numberService = numberService;
  }

  @GetMapping(value = "/numbers")
  public ResponseEntity<List<NumberDTO>> numbers(
    @RequestParam(name = "player") Long playerId,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info(
      "Numbers of player {} requested by player {}",
      playerId,
      jwtToken.getName()
    );

    final List<NumberDTO> numbers = numberService.getNumbersByPlayerId(
      AuthDTO.fromJwtToken(jwtToken),
      playerId
    );

    return ResponseEntity.ok(numbers);
  }

  @PostMapping(value = "/numbers")
  public ResponseEntity<List<NumberDTO>> newNumbers(
    @RequestParam(name = "player") Long playerId,
    @Valid @RequestBody NewNumbersRequest newNumbersRequest,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info("Adding new numbers for player {}", playerId);

    final List<NumberDTO> numbers = numberService.newNumbers(
      AuthDTO.fromJwtToken(jwtToken),
      playerId,
      newNumbersRequest.first(),
      newNumbersRequest.second(),
      newNumbersRequest.third()
    );

    return ResponseEntity.ok(numbers);
  }
}
