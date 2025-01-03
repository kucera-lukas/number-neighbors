package com.lukaskucera.numberneighbors.repository;

import com.lukaskucera.numberneighbors.entity.NumberEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberRepository extends JpaRepository<NumberEntity, Long> {
  List<NumberEntity> findNumberEntitiesByPlayerId(Long playerId);

  Optional<NumberEntity> findNumberEntityByValueAndIsGuessedAndPlayerId(
    int value,
    boolean isGuessed,
    Long playerId
  );
}
