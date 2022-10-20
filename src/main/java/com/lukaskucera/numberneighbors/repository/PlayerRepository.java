package com.lukaskucera.numberneighbors.repository;

import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {}
