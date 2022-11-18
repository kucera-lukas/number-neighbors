package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.dto.AuthDTO;
import com.lukaskucera.numberneighbors.dto.GameDTO;
import com.lukaskucera.numberneighbors.dto.PlayerDTO;
import com.lukaskucera.numberneighbors.dto.UserGameDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    final GameDTO game = gameService.newGame();
    final PlayerDTO player = playerService.newPlayer(
      game.id(),
      newGameRequest.hostName()
    );

    logger.info("Game {} created for host player {}", game.id(), player.id());

    final String token = jwtService.generatePlayerToken(player);

    logger.info(
      "Generated JWT token for host player {} in game {}",
      player.id(),
      game.id()
    );

    return ResponseEntity.ok(new NewGameResponse(game, token));
  }

  @GetMapping(value = "/games/{id}")
  public ResponseEntity<GameDTO> game(
    @PathVariable Long id,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info("Game {} requested by player {}", id, jwtToken.getName());

    final GameDTO game = gameService.getGameById(
      AuthDTO.fromJwtToken(jwtToken),
      id
    );

    return ResponseEntity.ok(game);
  }

  @GetMapping(value = "/games/{id}/payload")
  public ResponseEntity<UserGameDTO> gamePayload(
    @PathVariable Long id,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info(
      "Game payload {} requested by player {}",
      id,
      jwtToken.getName()
    );

    final UserGameDTO userGame = playerService.getPlayerUserGameByGameId(
      AuthDTO.fromJwtToken(jwtToken),
      id
    );

    return ResponseEntity.ok(userGame);
  }
}
