package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.entity.Game;
import com.lukaskucera.numberneighbors.request.NewGameRequest;
import com.lukaskucera.numberneighbors.service.GameService;
import com.lukaskucera.numberneighbors.service.GameServiceImpl;
import com.lukaskucera.numberneighbors.service.JwtService;
import org.jetbrains.annotations.NotNull;
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

  @GetMapping("/token")
  public ResponseEntity<Token> getToken(@NotNull JwtAuthenticationToken jwtToken) {
    return ResponseEntity.ok(new Token(jwtToken.getToken(), jwtToken.getAuthorities()));
  }

  @PostMapping("/token")
  public ResponseEntity<String> newToken() {
    return ResponseEntity.ok(jwtService.generate());
  }

  @PostMapping(value = "/games")
  public ResponseEntity<Game> newGame(@RequestBody @NotNull NewGameRequest newGameRequest) {
    return ResponseEntity.ok(gameService.newGame(newGameRequest.hostName()));
  }

  @GetMapping(value = "/games/{id}")
  public ResponseEntity<Game> game(@PathVariable Long id) {
    return ResponseEntity.ok(gameService.getGameById(id));
  }

  @DeleteMapping(value = "/games/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteGame(@PathVariable Long id) {
    gameService.deleteGameById(id);
  }
}
