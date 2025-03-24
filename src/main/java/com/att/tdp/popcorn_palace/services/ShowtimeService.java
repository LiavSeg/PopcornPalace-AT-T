package com.att.tdp.popcorn_palace.services;

import com.att.tdp.popcorn_palace.domain.Entities.ShowtimeEntity;
import org.springframework.stereotype.Component;

@Component
public interface ShowtimeService {
    ShowtimeEntity createShowtime(ShowtimeEntity showtimeEntity);
    ShowtimeEntity findById(Integer id);
    void deleteShowtime(Integer id);

}
