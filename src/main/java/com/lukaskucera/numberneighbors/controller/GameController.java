package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.entity.Game;
import com.lukaskucera.numberneighbors.entity.Player;
import com.lukaskucera.numberneighbors.request.NewGameRequest;
import com.lukaskucera.numberneighbors.response.NewGameResponse;
import com.lukaskucera.numberneighbors.service.GameService;
import com.lukaskucera.numberneighbors.service.GameServiceImpl;
import com.lukaskucera.numberneighbors.service.JwtService;
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

  private final GameService gameService;

  private final JwtService jwtService;

  public GameController(GameServiceImpl gameService, JwtService jwtService) {
    this.gameService = gameService;
    this.jwtService = jwtService;
  }

  @PostMapping(value = "/games")
  public ResponseEntity<NewGameResponse> newGame(
    @RequestBody NewGameRequest newGameRequest
  ) {
    final Game game = gameService.newGame(newGameRequest.hostName());
    final Player hostPlayer = game.getHost();
    final String token = jwtService.generatePlayerToken(
      hostPlayer.getName(),
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
  }
}
