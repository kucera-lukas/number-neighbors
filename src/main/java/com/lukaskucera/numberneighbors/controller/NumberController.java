package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.entity.NumberEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.request.NewNumbersRequest;
import com.lukaskucera.numberneighbors.service.GameServiceImpl;
import com.lukaskucera.numberneighbors.service.NumberServiceImpl;
import com.lukaskucera.numberneighbors.service.PlayerServiceImpl;
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

  private final GameServiceImpl gameService;
  private final PlayerServiceImpl playerService;
  private final NumberServiceImpl numberService;

  public NumberController(
    GameServiceImpl gameService,
    PlayerServiceImpl playerService,
    NumberServiceImpl numberService
  ) {
    this.gameService = gameService;
    this.playerService = playerService;
    this.numberService = numberService;
  }

  @GetMapping(value = "/numbers")
  public ResponseEntity<List<NumberEntity>> numbers(
    @RequestParam(name = "player") Long playerId,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info(
      "Numbers of player {} requested by player {}",
      playerId,
      jwtToken.getName()
    );

    playerService.checkPlayerAccess(playerId, jwtToken);

    return ResponseEntity.ok(numberService.getNumbersByPlayerId(playerId));
  }

  @PostMapping(value = "/numbers")
  public ResponseEntity<List<NumberEntity>> newNumbers(
    @RequestParam(name = "player") Long playerId,
    @Valid @RequestBody NewNumbersRequest newNumbersRequest,
    JwtAuthenticationToken jwtToken
  ) {
    playerService.checkPlayerAccess(playerId, jwtToken);

    numberService.validateNumbers(
      newNumbersRequest.first(),
      newNumbersRequest.second(),
      newNumbersRequest.third()
    );

    final PlayerEntity player = playerService.getPlayerById(playerId);

    logger.info("Adding new numbers for player {}", player.getId());

    final List<NumberEntity> numbers = numberService.addNumbersToPlayer(
      player,
      newNumbersRequest.first(),
      newNumbersRequest.second(),
      newNumbersRequest.third()
    );

    gameService.sendPayloadToPlayers(player.getGame());

    return ResponseEntity.ok(numbers);
  }
}
