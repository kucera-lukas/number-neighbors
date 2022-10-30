package com.lukaskucera.numberneighbors.repository;

import com.lukaskucera.numberneighbors.entity.ResponseEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository
  extends JpaRepository<ResponseEntity, Long> {
  List<ResponseEntity> findResponseEntitiesByPlayerId(Long playerId);
}
