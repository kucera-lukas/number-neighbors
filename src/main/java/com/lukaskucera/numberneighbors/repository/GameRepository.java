package com.lukaskucera.numberneighbors.repository;

import com.lukaskucera.numberneighbors.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {}
