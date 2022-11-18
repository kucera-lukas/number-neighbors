package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.dto.AuthDTO;
import com.lukaskucera.numberneighbors.dto.ResponseDTO;
import com.lukaskucera.numberneighbors.request.NewResponseRequest;
import com.lukaskucera.numberneighbors.service.ResponseServiceImpl;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResponseController {

  private static final Logger logger = LoggerFactory.getLogger(
    ResponseController.class
  );

  private final ResponseServiceImpl responseService;

  public ResponseController(ResponseServiceImpl responseService) {
    this.responseService = responseService;
  }

  @GetMapping("/responses")
  public ResponseEntity<List<ResponseDTO>> responses(
    @RequestParam(name = "player") Long playerId,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info("Responses requested by player {}", jwtToken.getName());

    final List<ResponseDTO> responses = responseService.getResponsesByPlayerId(
      AuthDTO.fromJwtToken(jwtToken),
      playerId
    );

    return ResponseEntity.ok(responses);
  }

  @PostMapping("/responses")
  public ResponseEntity<ResponseDTO> newResponse(
    @RequestParam(name = "turn") Long turnId,
    @Valid @RequestBody NewResponseRequest newResponseRequest,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info(
      "New response to turn {} by player {}",
      turnId,
      jwtToken.getName()
    );

    final ResponseDTO response = responseService.newResponse(
      AuthDTO.fromJwtToken(jwtToken),
      turnId,
      newResponseRequest.type()
    );

    return ResponseEntity.ok(response);
  }
}
