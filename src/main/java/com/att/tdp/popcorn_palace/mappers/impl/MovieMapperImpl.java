package com.att.tdp.popcorn_palace.mappers.impl;

import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import com.att.tdp.popcorn_palace.domain.dto.MovieDto;
import com.att.tdp.popcorn_palace.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MovieMapperImpl implements Mapper<MovieEntity, MovieDto> {

    private ModelMapper modelMapper;

    public MovieMapperImpl(ModelMapper modelMapper) {
        this.modelMapper =modelMapper;

    }


    @Override
    public MovieDto mapTo(MovieEntity movieEntity) {
        return modelMapper.map(movieEntity, MovieDto.class);

    }

    @Override
    public MovieEntity mapFrom(MovieDto movieDto) {
        return modelMapper.map(movieDto, MovieEntity.class);
    }
}
