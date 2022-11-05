package com.lukaskucera.numberneighbors.request;

import com.lukaskucera.numberneighbors.service.NumberServiceImpl;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record NewTurnRequest(
  @NotNull
  @Range(min = NumberServiceImpl.MIN_NUMBER, max = NumberServiceImpl.MAX_NUMBER)
  Integer value
) {}
