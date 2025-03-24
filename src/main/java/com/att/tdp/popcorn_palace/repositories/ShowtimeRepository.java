package com.att.tdp.popcorn_palace.repositories;

import com.att.tdp.popcorn_palace.domain.Entities.ShowtimeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowtimeRepository extends CrudRepository<ShowtimeEntity,Integer>{
    Iterable<ShowtimeEntity> findByTheater(String theater);
    Iterable<ShowtimeEntity> findByMovieId(Integer movieId);

}
