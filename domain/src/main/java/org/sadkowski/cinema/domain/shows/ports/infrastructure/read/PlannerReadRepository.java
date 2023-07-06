package org.sadkowski.cinema.domain.shows.ports.infrastructure.read;

import org.sadkowski.cinema.domain.shows.model.Planner;

import java.util.Optional;
import java.util.UUID;

public interface PlannerReadRepository {
    Optional<Planner> findAllById(UUID movieId);
}
