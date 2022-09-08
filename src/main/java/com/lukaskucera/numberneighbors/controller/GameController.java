package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.entity.Game;
import com.lukaskucera.numberneighbors.service.GameService;
import com.lukaskucera.numberneighbors.service.GameServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
  private final GameService gameService;

  public GameController(GameServiceImpl gameService) {
    this.gameService = gameService;
  }

  @GetMapping(value = "/hello")
  public String welcome(@RequestParam(value = "name", defaultValue = "Spring") String name) {
    return String.format("Welcome to Number Neighbors %s!", name);
  }

  @PostMapping(value = "/games")
  public ResponseEntity<Game> newGame(
      @RequestParam(name = "name", defaultValue = "host") String name) {
    return ResponseEntity.ok(gameService.newGameWithPlayer(name));
  }

  @GetMapping(value = "/games/{id}")
  public ResponseEntity<Game> game(@PathVariable Long id) {
    return ResponseEntity.ok(gameService.getGameById(id));
  }

  @PostMapping(value = "/games/{id}")
  public ResponseEntity<Game> updateGame(
      @PathVariable Long id, @RequestParam(name = "name", defaultValue = "guest") String name) {
    return ResponseEntity.ok(gameService.updateGameWithPlayer(id, name));
  }
}
