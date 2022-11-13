package com.lukaskucera.numberneighbors.dto;

import com.lukaskucera.numberneighbors.entity.NumberEntity;
import com.lukaskucera.numberneighbors.enums.NumberType;

public record NumberDTO(int value, boolean guessed, NumberType type) {
  public static NumberDTO fromNumber(NumberEntity number) {
    return new NumberDTO(
      number.getValue(),
      number.getIsGuessed(),
      number.getType()
    );
  }
}
