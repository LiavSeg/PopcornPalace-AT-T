package com.att.tdp.popcorn_palace.mappers;

import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import com.att.tdp.popcorn_palace.domain.dto.MovieDto;

public interface Mapper<A,B>{
    B mapTo(A a);
    A mapFrom(B b);


}
