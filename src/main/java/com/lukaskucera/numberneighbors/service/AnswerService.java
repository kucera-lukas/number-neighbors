package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.AnswerEntity;
import com.lukaskucera.numberneighbors.entity.ResponseEntity;
import com.lukaskucera.numberneighbors.enums.AnwserType;
import java.util.List;

public interface AnswerService {
  List<AnswerEntity> getAnswersByPlayerId(Long id);

  AnswerEntity newAnswer(ResponseEntity response, AnwserType type);
}
