package com.lukaskucera.numberneighbors.payload;

import com.lukaskucera.numberneighbors.entity.NumberEntity;
import com.lukaskucera.numberneighbors.enums.NumberType;

public record NumberPayload(int value, boolean guessed, NumberType type) {
  public static NumberPayload fromNumber(NumberEntity number) {
    return new NumberPayload(
      number.getValue(),
      number.getIsGuessed(),
      number.getType()
    );
  }
}
