package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.entity.Game;
import com.lukaskucera.numberneighbors.entity.Player;
import com.lukaskucera.numberneighbors.request.NewGameRequest;
import com.lukaskucera.numberneighbors.response.NewGameResponse;
import com.lukaskucera.numberneighbors.service.GameService;
import com.lukaskucera.numberneighbors.service.GameServiceImpl;
import com.lukaskucera.numberneighbors.service.JwtService;
import com.lukaskucera.numberneighbors.service.PlayerServiceImpl;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

  private final PlayerServiceImpl playerService;

  private final JwtService jwtService;

  private final SimpMessagingTemplate simpMessagingTemplate;

  public GameController(
    GameServiceImpl gameService,
    PlayerServiceImpl playerService,
    JwtService jwtService,
    SimpMessagingTemplate simpMessagingTemplate
  ) {
    this.gameService = gameService;
    this.playerService = playerService;
    this.jwtService = jwtService;
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  @PostMapping(value = "/games")
  public ResponseEntity<NewGameResponse> newGame(
    @RequestBody NewGameRequest newGameRequest
  ) {
    final Game game = gameService.newGame(newGameRequest.hostName());
    final Player hostPlayer = game.getHost();

    logger.info(
      "Game {} created for host player {}",
      game.getId(),
      hostPlayer.getId()
    );

    final String token = jwtService.generatePlayerToken(
      hostPlayer.getName(),
      hostPlayer.getId(),
      game.getId()
    );

    logger.info(
      "Generated JWT token for host player {} in game {}",
      hostPlayer.getId(),
      game.getId()
    );

    return ResponseEntity.ok(new NewGameResponse(game, token));
  }

  @GetMapping(value = "/games/{id}")
  public ResponseEntity<Game> game(
    @PathVariable Long id,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info(
      "Game {} info requested by player \"{}\"",
      id,
      jwtToken.getName()
    );

    gameService.checkGameAccess(id, jwtToken);
    return ResponseEntity.ok(gameService.getGameById(id));
  }

  @DeleteMapping(value = "/games/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteGame(
    @PathVariable Long id,
    JwtAuthenticationToken jwtToken
  ) {
    gameService.checkGameAccess(id, jwtToken);
    gameService.deleteGameById(id);

    logger.info("Game {} deleted by player \"{}\"", id, jwtToken.getName());
  }

  @MessageMapping(value = "/games/{id}/turn")
  @Transactional
  public void processTurn(
    @DestinationVariable Long id,
    @Payload String payload,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info("Processing turn on game id: {}", id);

    gameService.checkGameAccess(id, jwtToken);

    final Player player = playerService.getPlayerById(
      jwtToken.getToken().getClaim("playerId")
    );
    final Player otherPlayer = player.isHost()
      ? player.getGame().getGuest()
      : player;

    // custom handshake for player id as username (conflict on different games)
    simpMessagingTemplate.convertAndSendToUser(
      player.getName(),
      "/queue/turns",
      "payload: me = " + player + " from " + player.getName()
    );
  }
}
