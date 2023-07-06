package org.sadkowski.cinema.domain.shows.ports.application.query;

import org.sadkowski.cinema.domain.shows.model.Planner;

import java.util.Optional;
import java.util.UUID;

public interface PlannerQueryRepository {
    Optional<Planner> findAllById(UUID uuid);
}
