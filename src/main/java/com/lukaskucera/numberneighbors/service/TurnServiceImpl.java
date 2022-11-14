package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.dto.AuthDTO;
import com.lukaskucera.numberneighbors.dto.TurnDTO;
import com.lukaskucera.numberneighbors.entity.GameEntity;
import com.lukaskucera.numberneighbors.entity.NumberEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.entity.TurnEntity;
import com.lukaskucera.numberneighbors.exception.GameNotReadyException;
import com.lukaskucera.numberneighbors.exception.PlayerNotFoundException;
import com.lukaskucera.numberneighbors.exception.PlayerNotOnTurnException;
import com.lukaskucera.numberneighbors.exception.TurnNotCompletedException;
import com.lukaskucera.numberneighbors.exception.TurnRequiresAvailableNumberException;
import com.lukaskucera.numberneighbors.exception.TurnRequiresChosenNumberException;
import com.lukaskucera.numberneighbors.repository.PlayerRepository;
import com.lukaskucera.numberneighbors.repository.TurnRepository;
import java.util.List;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class TurnServiceImpl implements TurnService {

  private final GameServiceImpl gameService;

  private final SimpMessagingTemplate simpMessagingTemplate;

  private final PlayerRepository playerRepository;
  private final TurnRepository turnRepository;

  public TurnServiceImpl(
    GameServiceImpl gameService,
    SimpMessagingTemplate simpMessagingTemplate,
    PlayerRepository playerRepository,
    TurnRepository turnRepository
  ) {
    this.gameService = gameService;
    this.simpMessagingTemplate = simpMessagingTemplate;
    this.playerRepository = playerRepository;
    this.turnRepository = turnRepository;
  }

  @Override
  public List<TurnDTO> getTurnsByGameId(AuthDTO auth, Long gameId) {
    gameService.checkGameAccess(auth, gameId);

    return turnRepository
      .findTurnEntitiesByGameId(auth.gameId())
      .stream()
      .map(TurnDTO::fromTurn)
      .toList();
  }

  @Override
  public TurnDTO newTurn(AuthDTO auth, Long gameId, int value) {
    gameService.checkGameAccess(auth, gameId);

    final PlayerEntity player = playerRepository
      .findById(auth.playerId())
      .orElseThrow(() -> new PlayerNotFoundException(auth.playerId()));

    checkGameState(player, value);

    final TurnEntity turn = createTurn(value, player);
    final TurnDTO turnDTO = TurnDTO.fromTurn(turn);

    simpMessagingTemplate.convertAndSendToUser(
      player.getOpponent().getSub(),
      "/queue/turns",
      turnDTO
    );

    return turnDTO;
  }

  public void checkGameState(PlayerEntity player, int value) {
    final GameEntity game = player.getGame();

    if (!game.isReady()) {
      throw new GameNotReadyException(game.getId());
    }

    checkGameTurns(player);
    checkNumbers(player, value);
  }

  public TurnEntity createTurn(int value, PlayerEntity player) {
    final TurnEntity turn = new TurnEntity(value, player);

    player.addTurn(turn);
    player.getGame().addTurn(turn);

    turnRepository.save(turn);

    return turn;
  }

  public void checkGameTurns(PlayerEntity player) {
    final List<TurnEntity> gameTurns = player.getGame().getTurns();
    final int gameTurnCount = gameTurns.size();

    if (gameTurnCount == 0) {
      checkFirstTurn(player);
    } else {
      checkLastTurn(player, gameTurns.get(gameTurnCount - 1));
    }
  }

  public void checkNumbers(PlayerEntity player, int value) {
    checkChosenNumbers(player, value);
    checkAvailableNumbers(player, value);
  }

  public void checkFirstTurn(PlayerEntity player) {
    if (player.isGuest()) {
      throw new PlayerNotOnTurnException(player.getId());
    }
  }

  public void checkLastTurn(PlayerEntity player, TurnEntity lastTurn) {
    // current player must not have played the last turn
    if (lastTurn.getPlayer().getId().equals(player.getId())) {
      throw new PlayerNotOnTurnException(player.getId());
    }

    if (!lastTurn.isComplete()) {
      throw new TurnNotCompletedException(lastTurn.getId());
    }
  }

  public void checkChosenNumbers(PlayerEntity player, int value) {
    final List<TurnEntity> playerTurns = player.getTurns();
    final int playerTurnCount = playerTurns.size();

    if (playerTurnCount < 2) {
      return;
    }

    final List<Integer> chosenNumbers = player
      .getNumbers()
      .stream()
      .filter(number -> !number.getIsGuessed())
      .map(NumberEntity::getValue)
      .toList();
    final boolean mustBeChosen = playerTurns
      .subList(playerTurnCount - 2, playerTurnCount)
      .stream()
      .noneMatch(turn -> chosenNumbers.contains(turn.getValue()));

    if (mustBeChosen && !chosenNumbers.contains(value)) {
      throw new TurnRequiresChosenNumberException(value);
    }
  }

  public void checkAvailableNumbers(PlayerEntity player, int value) {
    if (
      player
        .getNumbers()
        .stream()
        .filter(number -> !number.getIsGuessed())
        .map(NumberEntity::getAvailableNumbers)
        .flatMap(List::stream)
        .distinct()
        .noneMatch(number -> number.equals(value))
    ) {
      throw new TurnRequiresAvailableNumberException(value);
    }
  }
}
