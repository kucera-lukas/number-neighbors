package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.ResponseEntity;
import com.lukaskucera.numberneighbors.entity.TurnEntity;
import com.lukaskucera.numberneighbors.enums.ResponseType;
import com.lukaskucera.numberneighbors.repository.ResponseRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ResponseServiceImpl implements ResponseService {

  private final ResponseRepository responseRepository;

  public ResponseServiceImpl(ResponseRepository responseRepository) {
    this.responseRepository = responseRepository;
  }

  @Override
  public List<ResponseEntity> getResponsesByPlayerId(Long id) {
    return responseRepository.findResponseEntitiesByPlayerId(id);
  }

  @Override
  public ResponseEntity newResponse(TurnEntity turn, ResponseType type) {
    final ResponseEntity response = new ResponseEntity(type, turn);

    turn.setResponse(response);
    turn.getPlayer().getOtherPlayer().addResponse(response);

    responseRepository.save(response);

    return response;
  }
}
