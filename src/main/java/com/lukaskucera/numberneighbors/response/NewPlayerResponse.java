package com.lukaskucera.numberneighbors.response;

import com.lukaskucera.numberneighbors.dto.PlayerDTO;

public record NewPlayerResponse(PlayerDTO player, String token) {}
