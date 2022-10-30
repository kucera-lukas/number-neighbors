package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.entity.TurnEntity;
import com.lukaskucera.numberneighbors.request.NewResponseRequest;
import com.lukaskucera.numberneighbors.service.PlayerServiceImpl;
import com.lukaskucera.numberneighbors.service.ResponseServiceImpl;
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
public class ResponseController {

  private static final Logger logger = LoggerFactory.getLogger(
    ResponseController.class
  );

  private final PlayerServiceImpl playerService;
  private final TurnServiceImpl turnService;
  private final ResponseServiceImpl responseService;
  private final SimpMessagingTemplate simpMessagingTemplate;

  public ResponseController(
    PlayerServiceImpl playerService,
    TurnServiceImpl turnService,
    ResponseServiceImpl responseService,
    SimpMessagingTemplate simpMessagingTemplate
  ) {
    this.playerService = playerService;
    this.turnService = turnService;
    this.responseService = responseService;
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  @GetMapping("/responses")
  public ResponseEntity<List<com.lukaskucera.numberneighbors.entity.ResponseEntity>> responses(
    @RequestParam(name = "player") Long playerId,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info("Responses requested by player {}", jwtToken.getName());

    playerService.checkPlayerAccess(playerId, jwtToken);

    return ResponseEntity.ok(responseService.getResponsesByPlayerId(playerId));
  }

  @PostMapping("/responses")
  public ResponseEntity<com.lukaskucera.numberneighbors.entity.ResponseEntity> newResponse(
    @RequestParam(name = "turn") Long turnId,
    @Valid @RequestBody NewResponseRequest newResponseRequest,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info(
      "New response to turn {} by player {}",
      turnId,
      jwtToken.getName()
    );

    final TurnEntity turn = turnService.getTurnById(turnId);
    final PlayerEntity otherPlayer = turn.getPlayer();
    final PlayerEntity currentPlayer = otherPlayer.getOtherPlayer();

    playerService.checkPlayerAccess(currentPlayer.getId(), jwtToken);
    turnService.checkTurnNeedsResponse(turn);

    responseService.newResponse(turn, newResponseRequest.type());

    simpMessagingTemplate.convertAndSendToUser(
      otherPlayer.getSub(),
      "/queue/turns",
      turn
    );

    return ResponseEntity.ok(turn.getResponse());
  }
}
