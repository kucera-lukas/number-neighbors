package com.lukaskucera.numberneighbors.request;

import com.lukaskucera.numberneighbors.enums.ResponseType;
import com.lukaskucera.numberneighbors.validation.EnumValidator;
import javax.validation.constraints.NotNull;

public record NewResponseRequest(
  @NotNull @EnumValidator(ResponseType.class) ResponseType type
) {}
