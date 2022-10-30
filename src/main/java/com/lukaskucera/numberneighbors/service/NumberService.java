package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.NumberEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import java.util.List;

public interface NumberService {
  List<NumberEntity> getNumbersByPlayerId(Long playerId);

  List<NumberEntity> addNumbersToPlayer(
    PlayerEntity player,
    int first,
    int second,
    int third
  );

  boolean isNumberInRange(int number);

  boolean areNumbersDistinct(int... numbers);

  void validateNumbers(int... numbers);
}
