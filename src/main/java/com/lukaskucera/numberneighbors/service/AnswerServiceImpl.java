package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.dto.AnswerDTO;
import com.lukaskucera.numberneighbors.dto.AuthDTO;
import com.lukaskucera.numberneighbors.dto.TurnDTO;
import com.lukaskucera.numberneighbors.entity.AnswerEntity;
import com.lukaskucera.numberneighbors.entity.NumberEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.entity.ResponseEntity;
import com.lukaskucera.numberneighbors.entity.TurnEntity;
import com.lukaskucera.numberneighbors.enums.AnwserType;
import com.lukaskucera.numberneighbors.exception.AnswerRequiresYesException;
import com.lukaskucera.numberneighbors.exception.NumberNotFoundException;
import com.lukaskucera.numberneighbors.exception.ResponseNotFoundException;
import com.lukaskucera.numberneighbors.repository.AnswerRepository;
import com.lukaskucera.numberneighbors.repository.NumberRepository;
import com.lukaskucera.numberneighbors.repository.ResponseRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {

  PlayerServiceImpl playerService;
  ResponseServiceImpl responseService;

  SimpMessagingTemplate simpMessagingTemplate;

  NumberRepository numberRepository;
  ResponseRepository responseRepository;
  AnswerRepository answerRepository;

  public AnswerServiceImpl(
    PlayerServiceImpl playerService,
    ResponseServiceImpl responseService,
    SimpMessagingTemplate simpMessagingTemplate,
    NumberRepository numberRepository,
    ResponseRepository responseRepository,
    AnswerRepository answerRepository
  ) {
    this.playerService = playerService;
    this.responseService = responseService;
    this.simpMessagingTemplate = simpMessagingTemplate;
    this.numberRepository = numberRepository;
    this.responseRepository = responseRepository;
    this.answerRepository = answerRepository;
  }

  @Override
  public List<AnswerDTO> getAnswersByPlayerId(AuthDTO auth, Long playerId) {
    playerService.checkPlayerAccess(auth, playerId);

    return answerRepository
      .findAnswerEntitiesByPlayerId(playerId)
      .stream()
      .map(AnswerDTO::fromAnswer)
      .toList();
  }

  @Override
  public AnswerDTO newAnswer(AuthDTO auth, Long responseId, AnwserType type) {
    final ResponseEntity response = responseRepository
      .findById(responseId)
      .orElseThrow(() -> new ResponseNotFoundException(responseId));
    final PlayerEntity opponent = response.getPlayer();
    final PlayerEntity player = opponent.getOpponent();

    playerService.checkPlayerAccess(auth, player.getId());
    responseService.checkResponseNeedsAnswer(response);

    checkAnswerType(type, response);

    final AnswerEntity answer = createAnswer(type, response);

    simpMessagingTemplate.convertAndSendToUser(
      opponent.getSub(),
      "/queue/turns",
      TurnDTO.fromTurn(response.getTurn())
    );

    return AnswerDTO.fromAnswer(answer);
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

  public AnswerEntity createAnswer(AnwserType type, ResponseEntity response) {
    final AnswerEntity answer = new AnswerEntity(type, response);

    response.setAnswer(answer);
    response.getPlayer().addAnswer(answer);

    answerRepository.save(answer);

    return answer;
  }
}
