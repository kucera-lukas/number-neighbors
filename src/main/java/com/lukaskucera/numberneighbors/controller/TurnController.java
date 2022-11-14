package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.dto.AuthDTO;
import com.lukaskucera.numberneighbors.dto.TurnDTO;
import com.lukaskucera.numberneighbors.request.NewTurnRequest;
import com.lukaskucera.numberneighbors.service.TurnServiceImpl;
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
public class TurnController {

  private static final Logger logger = LoggerFactory.getLogger(
    TurnController.class
  );

  private final TurnServiceImpl turnService;

  public TurnController(TurnServiceImpl turnService) {
    this.turnService = turnService;
  }

  @GetMapping("/turns")
  public ResponseEntity<List<TurnDTO>> turns(
    @RequestParam(name = "game") Long gameId,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info("Turns requested by player {}", jwtToken.getName());

    final List<TurnDTO> turns = turnService.getTurnsByGameId(
      AuthDTO.fromJwtToken(jwtToken),
      gameId
    );

    return ResponseEntity.ok(turns);
  }

  @PostMapping("/turns")
  public ResponseEntity<TurnDTO> newTurn(
    @RequestParam(name = "game") Long gameId,
    @Valid @RequestBody NewTurnRequest newTurnRequest,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info("New turn in game {} by player {}", gameId, jwtToken.getName());

    final TurnDTO turn = turnService.newTurn(
      AuthDTO.fromJwtToken(jwtToken),
      gameId,
      newTurnRequest.value()
    );

    return ResponseEntity.ok(turn);
  }
}
