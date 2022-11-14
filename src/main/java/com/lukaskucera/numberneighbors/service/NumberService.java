package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.dto.AuthDTO;
import com.lukaskucera.numberneighbors.dto.NumberDTO;
import java.util.List;

public interface NumberService {
  List<NumberDTO> getNumbersByPlayerId(AuthDTO auth, Long playerId);

  List<NumberDTO> newNumbers(
    AuthDTO auth,
    Long playerId,
    int first,
    int second,
    int third
  );
}
