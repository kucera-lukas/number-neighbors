package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.entity.Player;
import com.lukaskucera.numberneighbors.request.NewPlayerRequest;
import com.lukaskucera.numberneighbors.response.NewPlayerResponse;
import com.lukaskucera.numberneighbors.service.JwtService;
import com.lukaskucera.numberneighbors.service.PlayerServiceImpl;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
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
  private final JwtService jwtService;

  public PlayerController(PlayerServiceImpl playerService, JwtService jwtService) {
    this.playerService = playerService;
    this.jwtService = jwtService;
  }

  @GetMapping(value = "/players")
  public ResponseEntity<Set<Player>> players(@RequestParam(name = "game") Long gameId) {
    return ResponseEntity.ok(playerService.getPlayersByGameId(gameId));
  }

  @PostMapping(value = "/players")
  public ResponseEntity<NewPlayerResponse> newPlayer(
      @RequestParam(name = "game") Long gameId,
      @RequestBody @NotNull NewPlayerRequest newPlayerRequest) {
    final Player player = playerService.newPlayer(gameId, newPlayerRequest.name());
    final String token = jwtService.generatePlayerToken(player.getName(), player.getId(), gameId);

    return ResponseEntity.ok(new NewPlayerResponse(player, token));
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
