package com.lukaskucera.numberneighbors;

import static org.assertj.core.api.Assertions.assertThat;

import com.lukaskucera.numberneighbors.controller.GameController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NumberNeighborsApplicationTests {

  @Autowired
  private GameController controller;

  @Test
  void contextLoads() {
    assertThat(controller).isNotNull();
  }
}
