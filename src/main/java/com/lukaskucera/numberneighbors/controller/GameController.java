package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.entity.GameEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.payload.GamePayload;
import com.lukaskucera.numberneighbors.request.NewGameRequest;
import com.lukaskucera.numberneighbors.response.NewGameResponse;
import com.lukaskucera.numberneighbors.service.GameService;
import com.lukaskucera.numberneighbors.service.GameServiceImpl;
import com.lukaskucera.numberneighbors.service.JwtService;
import com.lukaskucera.numberneighbors.service.PlayerService;
import com.lukaskucera.numberneighbors.service.PlayerServiceImpl;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

  private static final Logger logger = LoggerFactory.getLogger(
    GameController.class
  );

  private final GameService gameService;
  private final PlayerService playerService;
  private final JwtService jwtService;

  public GameController(
    GameServiceImpl gameService,
    PlayerServiceImpl playerService,
    JwtService jwtService
  ) {
    this.gameService = gameService;
    this.playerService = playerService;
    this.jwtService = jwtService;
  }

  @PostMapping(value = "/games")
  public ResponseEntity<NewGameResponse> newGame(
    @Valid @RequestBody NewGameRequest newGameRequest
  ) {
    final GameEntity game = gameService.newGame();
    final PlayerEntity player = playerService.newPlayer(
      newGameRequest.hostName(),
      game
    );

    logger.info(
      "Game {} created for host player {}",
      game.getId(),
      player.getId()
    );

    final String token = jwtService.generatePlayerToken(player);

    logger.info(
      "Generated JWT token for host player {} in game {}",
      player.getId(),
      game.getId()
    );

    return ResponseEntity.ok(
      new NewGameResponse(GamePayload.fromPlayer(player), token)
    );
  }

  @GetMapping(value = "/games/{id}")
  public ResponseEntity<GamePayload> game(
    @PathVariable Long id,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info("Game {} info requested by player {}", id, jwtToken.getName());

    gameService.checkGameAccess(id, jwtToken);

    final PlayerEntity player = playerService.getPlayerByJwtToken(jwtToken);

    return ResponseEntity.ok(GamePayload.fromPlayer(player));
  }

  @DeleteMapping(value = "/games/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteGame(
    @PathVariable Long id,
    JwtAuthenticationToken jwtToken
  ) {
    gameService.checkGameAccess(id, jwtToken);
    gameService.deleteGameById(id);

    logger.info("Game {} deleted by player {}", id, jwtToken.getName());
  }

  @GetMapping(value = "/games/payload")
  public ResponseEntity<GamePayload> gamePayload(
    JwtAuthenticationToken jwtToken
  ) {
    logger.info("Game payload requested by player {}", jwtToken.getName());

    final PlayerEntity player = playerService.getPlayerByJwtToken(jwtToken);

    return ResponseEntity.ok(GamePayload.fromPlayer(player));
  }
}
