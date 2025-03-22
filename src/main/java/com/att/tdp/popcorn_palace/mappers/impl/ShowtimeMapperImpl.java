package com.att.tdp.popcorn_palace.mappers.impl;

import com.att.tdp.popcorn_palace.domain.Entities.ShowtimeEntity;
import com.att.tdp.popcorn_palace.domain.dto.ShowtimeDto;
import com.att.tdp.popcorn_palace.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ShowtimeMapperImpl implements Mapper<ShowtimeEntity, ShowtimeDto> {

    private ModelMapper modelMapper;


    public ShowtimeMapperImpl (ModelMapper modelMapper) {this.modelMapper = modelMapper;}

    @Override
    public ShowtimeDto mapTo(ShowtimeEntity showtimeEntity) {
        return modelMapper.map(showtimeEntity,ShowtimeDto.class);
    }

    @Override
    public ShowtimeEntity mapFrom(ShowtimeDto showtimeDto) {
        return modelMapper.map(showtimeDto,ShowtimeEntity.class);
    }
}
