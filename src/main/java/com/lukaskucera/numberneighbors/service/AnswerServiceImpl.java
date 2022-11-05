package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.AnswerEntity;
import com.lukaskucera.numberneighbors.entity.NumberEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.entity.ResponseEntity;
import com.lukaskucera.numberneighbors.entity.TurnEntity;
import com.lukaskucera.numberneighbors.enums.AnwserType;
import com.lukaskucera.numberneighbors.exception.AnswerRequiresYesException;
import com.lukaskucera.numberneighbors.exception.NumberNotFoundException;
import com.lukaskucera.numberneighbors.repository.AnswerRepository;
import com.lukaskucera.numberneighbors.repository.NumberRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {

  NumberRepository numberRepository;
  AnswerRepository answerRepository;

  public AnswerServiceImpl(
    NumberRepository numberRepository,
    AnswerRepository answerRepository
  ) {
    this.numberRepository = numberRepository;
    this.answerRepository = answerRepository;
  }

  @Override
  public List<AnswerEntity> getAnswersByPlayerId(Long id) {
    return answerRepository.findAnswerEntitiesByPlayerId(id);
  }

  @Override
  public AnswerEntity newAnswer(ResponseEntity response, AnwserType type) {
    checkAnswerType(type, response);

    final AnswerEntity answer = new AnswerEntity(type, response);

    response.setAnswer(answer);
    response.getPlayer().addAnswer(answer);

    answerRepository.save(answer);

    return answer;
  }

  void checkAnswerType(AnwserType type, ResponseEntity response) {
    final TurnEntity turn = response.getTurn();
    final int turnValue = turn.getValue();
    final PlayerEntity player = turn.getPlayer();
    final Optional<NumberEntity> turnNumber = numberRepository.findNumberEntityByValueAndIsGuessedAndPlayerId(
      turnValue,
      false,
      player.getId()
    );

    if (type == AnwserType.YES) {
      final NumberEntity number = turnNumber.orElseThrow(() ->
        new NumberNotFoundException(turnValue, player.getId())
      );

      number.setIsGuessed(true);

      numberRepository.save(number);
    } else {
      turnNumber.ifPresent(number -> {
        throw new AnswerRequiresYesException(response.getId());
      });
    }
  }
}
