package org.sadkowski.cinema.domain.shows.ports.infrastructure.read;

import org.sadkowski.cinema.domain.shows.model.Movie;

import java.util.Optional;
import java.util.UUID;

public interface MovieReadRepository {

    Optional<Movie> findAllById(UUID movieId);
}
