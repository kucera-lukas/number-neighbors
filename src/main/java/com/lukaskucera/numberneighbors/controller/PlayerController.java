package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.dto.AuthDTO;
import com.lukaskucera.numberneighbors.dto.PlayerDTO;
import com.lukaskucera.numberneighbors.request.NewPlayerRequest;
import com.lukaskucera.numberneighbors.response.NewPlayerResponse;
import com.lukaskucera.numberneighbors.service.JwtService;
import com.lukaskucera.numberneighbors.service.PlayerServiceImpl;
import java.util.Set;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

  private static final Logger logger = LoggerFactory.getLogger(
    PlayerController.class
  );

  private final PlayerServiceImpl playerService;
  private final JwtService jwtService;

  public PlayerController(
    PlayerServiceImpl playerService,
    JwtService jwtService
  ) {
    this.playerService = playerService;
    this.jwtService = jwtService;
  }

  @GetMapping(value = "/players")
  public ResponseEntity<Set<PlayerDTO>> players(
    @RequestParam(name = "game") Long gameId,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info(
      "Players info of the game {} requested by player {}",
      gameId,
      jwtToken.getName()
    );

    final Set<PlayerDTO> players = playerService.getPlayersByGameId(
      AuthDTO.fromJwtToken(jwtToken),
      gameId
    );

    return ResponseEntity.ok(players);
  }

  @PostMapping(value = "/players")
  public ResponseEntity<NewPlayerResponse> newPlayer(
    @RequestParam(name = "game") Long gameId,
    @Valid @RequestBody NewPlayerRequest newPlayerRequest
  ) {
    final PlayerDTO player = playerService.newPlayer(
      gameId,
      newPlayerRequest.name()
    );

    logger.info("Guest player {} created in game {}", player.id(), gameId);

    final String token = jwtService.generatePlayerToken(player);

    logger.info(
      "Generated JWT token for guest player {} in game {}",
      player.id(),
      gameId
    );

    return ResponseEntity.ok(new NewPlayerResponse(player, token));
  }

  @GetMapping(value = "/players/{id}")
  public ResponseEntity<PlayerDTO> player(
    @PathVariable Long id,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info(
      "Player {} info requested by player {}",
      id,
      jwtToken.getName()
    );

    final PlayerDTO player = playerService.getPlayerById(
      AuthDTO.fromJwtToken(jwtToken),
      id
    );

    return ResponseEntity.ok(player);
  }

  @DeleteMapping(value = "/players/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deletePlayer(
    @PathVariable Long id,
    JwtAuthenticationToken jwtToken
  ) {
    playerService.deletePlayerById(AuthDTO.fromJwtToken(jwtToken), id);

    logger.info("Player {} deleted by player {}", id, jwtToken.getName());
  }
}
