package com.att.tdp.popcorn_palace.repositories;

import com.att.tdp.popcorn_palace.domain.Entities.BookingEntity;
import com.att.tdp.popcorn_palace.domain.dto.BookingDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends CrudRepository<BookingEntity, BookingDto> {
}
