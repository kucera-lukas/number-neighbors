package com.lukaskucera.numberneighbors.repository;

import com.lukaskucera.numberneighbors.entity.AnswerEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
  List<AnswerEntity> findAnswerEntitiesByPlayerId(Long playerId);
}
