package com.lukaskucera.numberneighbors.response;

import com.lukaskucera.numberneighbors.dto.GameDTO;

public record NewGameResponse(GameDTO game, String token) {}
