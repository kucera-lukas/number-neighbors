package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.entity.TurnEntity;
import com.lukaskucera.numberneighbors.request.NewTurnRequest;
import com.lukaskucera.numberneighbors.service.GameServiceImpl;
import com.lukaskucera.numberneighbors.service.PlayerServiceImpl;
import com.lukaskucera.numberneighbors.service.TurnServiceImpl;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

  private final GameServiceImpl gameService;
  private final PlayerServiceImpl playerService;
  private final TurnServiceImpl turnService;
  private final SimpMessagingTemplate simpMessagingTemplate;

  public TurnController(
    GameServiceImpl gameService,
    PlayerServiceImpl playerService,
    TurnServiceImpl turnService,
    SimpMessagingTemplate simpMessagingTemplate
  ) {
    this.gameService = gameService;
    this.playerService = playerService;
    this.turnService = turnService;
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  @GetMapping("/turns")
  public ResponseEntity<List<TurnEntity>> turns(
    @RequestParam(name = "game") Long gameId,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info("Turns requested by player {}", jwtToken.getName());

    gameService.checkGameAccess(gameId, jwtToken);

    return ResponseEntity.ok(turnService.getTurnsByGameId(gameId));
  }

  @PostMapping("/turns")
  public ResponseEntity<TurnEntity> newTurn(
    @RequestParam(name = "game") Long gameId,
    @Valid @RequestBody NewTurnRequest newTurnRequest,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info("New turn in game {} by player {}", gameId, jwtToken.getName());

    gameService.checkGameAccess(gameId, jwtToken);

    final PlayerEntity player = playerService.getPlayerByJwtToken(jwtToken);

    gameService.checkGameReady(player.getGame());

    final TurnEntity turn = turnService.newTurn(player, newTurnRequest.value());

    simpMessagingTemplate.convertAndSendToUser(
      player.getOtherPlayer().getSub(),
      "/queue/turns",
      turn
    );

    return ResponseEntity.ok(turn);
  }
}
