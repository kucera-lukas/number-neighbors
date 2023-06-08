package com.lukaskucera.numberneighbors.request;

import com.lukaskucera.numberneighbors.enums.AnswerType;
import javax.validation.constraints.NotNull;

public record NewAnswerRequest(@NotNull AnswerType type) {}
