package org.sadkowski.cinema.domain.shows.ports.infrastructure.write;

import org.sadkowski.cinema.domain.shows.model.AvailableHours;

public interface AvailableHoursWriteRepository {

    void save(AvailableHours availableHours);
}
