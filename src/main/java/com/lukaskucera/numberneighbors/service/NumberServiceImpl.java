package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.dto.AuthDTO;
import com.lukaskucera.numberneighbors.dto.NumberDTO;
import com.lukaskucera.numberneighbors.entity.NumberEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.enums.NumberType;
import com.lukaskucera.numberneighbors.exception.NumberOutOfRangeException;
import com.lukaskucera.numberneighbors.exception.NumbersNotDistinctException;
import com.lukaskucera.numberneighbors.exception.NumbersPopulatedException;
import com.lukaskucera.numberneighbors.exception.PlayerNotFoundException;
import com.lukaskucera.numberneighbors.repository.NumberRepository;
import com.lukaskucera.numberneighbors.repository.PlayerRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class NumberServiceImpl implements NumberService {

  public static final int MIN_NUMBER = 0;
  public static final int MAX_NUMBER = 15;

  private final GameServiceImpl gameService;
  private final PlayerServiceImpl playerService;

  private final PlayerRepository playerRepository;
  private final NumberRepository numberRepository;

  public NumberServiceImpl(
    GameServiceImpl gameService,
    PlayerServiceImpl playerService,
    PlayerRepository playerRepository,
    NumberRepository numberRepository
  ) {
    this.gameService = gameService;
    this.playerService = playerService;
    this.playerRepository = playerRepository;
    this.numberRepository = numberRepository;
  }

  @Override
  public List<NumberDTO> getNumbersByPlayerId(AuthDTO auth, Long playerId) {
    playerService.checkPlayerAccess(auth, playerId);

    return numberRepository
      .findNumberEntitiesByPlayerId(playerId)
      .stream()
      .map(NumberDTO::fromNumber)
      .toList();
  }

  @Override
  @Transactional
  public List<NumberDTO> newNumbers(
    AuthDTO auth,
    Long playerId,
    int first,
    int second,
    int third
  ) {
    playerService.checkPlayerAccess(auth, playerId);

    validateNumbers(first, second, third);

    final PlayerEntity player = playerRepository
      .findById(playerId)
      .orElseThrow(() -> new PlayerNotFoundException(playerId));

    if (!player.getNumbers().isEmpty()) {
      throw new NumbersPopulatedException(player.getId());
    }

    final List<NumberEntity> numbers = createNumbers(
      player,
      first,
      second,
      third
    );

    gameService.sendPayloadToPlayers(player.getGame());

    return numbers.stream().map(NumberDTO::fromNumber).toList();
  }

  public void validateNumbers(int... numbers) {
    if (!areNumbersDistinct(numbers)) {
      throw new NumbersNotDistinctException(numbers);
    }

    Arrays
      .stream(numbers)
      .forEach(number -> {
        if (!isNumberInRange(number)) {
          throw new NumberOutOfRangeException(number);
        }
      });
  }

  public List<NumberEntity> createNumbers(
    PlayerEntity player,
    int first,
    int second,
    int third
  ) {
    final List<NumberEntity> numbers = Map
      .of(
        NumberType.FIRST,
        first,
        NumberType.SECOND,
        second,
        NumberType.THIRD,
        third
      )
      .entrySet()
      .stream()
      .map(entry -> new NumberEntity(entry.getValue(), entry.getKey(), player))
      .toList();

    numbers.forEach(player::addNumber);

    playerRepository.save(player);

    return numbers;
  }

  public boolean areNumbersDistinct(int... numbers) {
    final Set<Integer> seen = new HashSet<>();

    for (int number : numbers) {
      if (!seen.add(number)) {
        return false;
      }
    }

    return true;
  }

  public boolean isNumberInRange(int number) {
    return number >= MIN_NUMBER && number <= MAX_NUMBER;
  }
}
