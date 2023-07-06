package org.sadkowski.cinema.domain.shows.ports.application.query;

import org.sadkowski.cinema.domain.shows.model.Movie;

import java.util.Optional;
import java.util.UUID;

public interface MovieQueryRepository {
    Optional<Movie> findAllById(UUID uuid);
}
