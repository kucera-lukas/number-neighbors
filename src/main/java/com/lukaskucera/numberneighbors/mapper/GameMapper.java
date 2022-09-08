package com.lukaskucera.numberneighbors.mapper;

import com.lukaskucera.numberneighbors.dto.GameDto;
import com.lukaskucera.numberneighbors.entity.Game;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = PlayerMapper.class)
public interface GameMapper {

  GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

  GameDto toDto(Game game);
}
