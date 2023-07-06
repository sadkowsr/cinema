package org.sadkowski.cinema.domain.shows.ports.infrastructure.read;

import org.sadkowski.cinema.domain.shows.model.ShowTypeDto;
import org.sadkowski.cinema.domain.shows.model.AvailableHours;

import java.util.Optional;

public interface AvailableHoursReadRepository {

    Optional<AvailableHours> findAllByShowType(ShowTypeDto type);
}
