package com.lukaskucera.numberneighbors.response;

import com.lukaskucera.numberneighbors.entity.Player;

public record NewPlayerResponse(Player player, String token) {}
