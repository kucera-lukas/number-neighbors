package com.lukaskucera.numberneighbors.request;

import com.lukaskucera.numberneighbors.enums.AnwserType;
import javax.validation.constraints.NotNull;

public record NewAnswerRequest(@NotNull AnwserType type) {}
