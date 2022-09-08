package com.lukaskucera.numberneighbors.mapper;

import com.lukaskucera.numberneighbors.dto.PlayerDto;
import com.lukaskucera.numberneighbors.entity.Player;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = GameMapper.class)
public interface PlayerMapper {

  PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

  PlayerDto toDto(Player entity);
}
