package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.entity.Player;
import com.lukaskucera.numberneighbors.request.NewPlayerRequest;
import com.lukaskucera.numberneighbors.response.NewPlayerResponse;
import com.lukaskucera.numberneighbors.service.GameServiceImpl;
import com.lukaskucera.numberneighbors.service.JwtService;
import com.lukaskucera.numberneighbors.service.PlayerServiceImpl;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

  private static final Logger logger = LoggerFactory.getLogger(
    PlayerController.class
  );

  private final GameServiceImpl gameService;
  private final PlayerServiceImpl playerService;
  private final JwtService jwtService;

  public PlayerController(
    GameServiceImpl gameService,
    PlayerServiceImpl playerService,
    JwtService jwtService
  ) {
    this.gameService = gameService;
    this.playerService = playerService;
    this.jwtService = jwtService;
  }

  @GetMapping(value = "/players")
  public ResponseEntity<Set<Player>> players(
    @RequestParam(name = "game") Long gameId,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info(
      "Players info of the game {} requested by player \"{}\"",
      gameId,
      jwtToken.getName()
    );

    gameService.checkGameAccess(gameId, jwtToken);
    return ResponseEntity.ok(playerService.getPlayersByGameId(gameId));
  }

  @PostMapping(value = "/players")
  public ResponseEntity<NewPlayerResponse> newPlayer(
    @RequestParam(name = "game") Long gameId,
    @RequestBody NewPlayerRequest newPlayerRequest
  ) {
    final Player player = playerService.newPlayer(
      gameId,
      newPlayerRequest.name()
    );

    logger.info("Guest player {} created in game {}", player.getId(), gameId);

    final String token = jwtService.generatePlayerToken(player);

    logger.info(
      "Generated JWT token for guest player {} in game {}",
      player.getId(),
      gameId
    );

    return ResponseEntity.ok(new NewPlayerResponse(player, token));
  }

  @GetMapping(value = "/players/{id}")
  public ResponseEntity<Player> player(
    @PathVariable Long id,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info(
      "Player {} info requested by player \"{}\"",
      id,
      jwtToken.getName()
    );

    playerService.checkPlayerAccess(id, jwtToken);
    return ResponseEntity.ok(playerService.getPlayerById(id));
  }

  @DeleteMapping(value = "/players/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deletePlayer(
    @PathVariable Long id,
    JwtAuthenticationToken jwtToken
  ) {
    playerService.checkPlayerAccess(id, jwtToken);
    playerService.deletePlayerById(id);

    logger.info("Player {} deleted by player \"{}\"", id, jwtToken.getName());
  }
}
