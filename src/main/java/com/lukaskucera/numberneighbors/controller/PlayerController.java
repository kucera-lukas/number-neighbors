package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.entity.Player;
import com.lukaskucera.numberneighbors.request.NewPlayerRequest;
import com.lukaskucera.numberneighbors.service.PlayerServiceImpl;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  private final PlayerServiceImpl playerService;

  public PlayerController(PlayerServiceImpl playerService) {
    this.playerService = playerService;
  }

  @GetMapping(value = "/players")
  public ResponseEntity<Set<Player>> players(@RequestParam(name = "game") Long gameId) {
    return ResponseEntity.ok(playerService.getPlayersByGameId(gameId));
  }

  @PostMapping(value = "/players")
  public ResponseEntity<Player> newPlayer(
      @RequestParam(name = "game") Long gameId, @RequestBody NewPlayerRequest newPlayerRequest) {
    return ResponseEntity.ok(playerService.newPlayer(gameId, newPlayerRequest.getName()));
  }

  @GetMapping(value = "/players/{id}")
  public ResponseEntity<Player> player(@PathVariable Long id) {
    return ResponseEntity.ok(playerService.getPlayerById(id));
  }

  @DeleteMapping(value = "/players/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deletePlayer(@PathVariable Long id) {
    playerService.deletePlayerById(id);
  }
}
