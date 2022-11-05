package com.lukaskucera.numberneighbors.request;

import com.lukaskucera.numberneighbors.enums.ResponseType;
import javax.validation.constraints.NotNull;

public record NewResponseRequest(@NotNull ResponseType type) {}
