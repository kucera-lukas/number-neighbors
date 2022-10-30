package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.NumberEntity;
import java.util.List;

public interface NumberService {
  List<NumberEntity> getNumbersByPlayerId(Long playerId);

  boolean isNumberInRange(int number);

  boolean areNumbersDistinct(int... numbers);

  void validateNumbers(int... numbers);
}
