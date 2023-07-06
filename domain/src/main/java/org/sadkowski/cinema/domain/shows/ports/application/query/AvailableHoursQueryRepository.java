package org.sadkowski.cinema.domain.shows.ports.application.query;

import org.sadkowski.cinema.domain.shows.model.AvailableHours;
import org.sadkowski.cinema.domain.shows.model.ShowTypeDto;

import java.util.Optional;

public interface AvailableHoursQueryRepository {
    Optional<AvailableHours> findAllByShowType(ShowTypeDto showTypeDto);
}
