package com.lukaskucera.numberneighbors.repository;

import com.lukaskucera.numberneighbors.entity.TurnEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnRepository extends JpaRepository<TurnEntity, Long> {
  List<TurnEntity> findTurnEntitiesByGameId(Long gameId);
}
