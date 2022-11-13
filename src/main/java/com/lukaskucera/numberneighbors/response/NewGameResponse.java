package com.lukaskucera.numberneighbors.response;

import com.lukaskucera.numberneighbors.payload.GamePayload;

public record NewGameResponse(GamePayload game, String token) {}
