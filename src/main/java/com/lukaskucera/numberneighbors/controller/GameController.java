package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.entity.Game;
import com.lukaskucera.numberneighbors.service.GameService;
import com.lukaskucera.numberneighbors.service.GameServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
  private final GameService gameService;

  public GameController(GameServiceImpl gameService) {
    this.gameService = gameService;
  }

  @PostMapping(value = "/games")
  public ResponseEntity<Game> newGame() {
    return ResponseEntity.ok(gameService.newGame());
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
