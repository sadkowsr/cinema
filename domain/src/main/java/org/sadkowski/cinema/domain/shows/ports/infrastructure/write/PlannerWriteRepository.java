package org.sadkowski.cinema.domain.shows.ports.infrastructure.write;

import org.sadkowski.cinema.domain.shows.model.Planner;

public interface PlannerWriteRepository {
    void save(Planner planer);
}
