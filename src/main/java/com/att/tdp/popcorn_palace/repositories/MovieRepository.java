package com.att.tdp.popcorn_palace.repositories;

import com.att.tdp.popcorn_palace.domain.Entities.MovieEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MovieRepository extends CrudRepository<MovieEntity, Integer> {

    Iterable<MovieEntity> findByTitle(String title);
}
