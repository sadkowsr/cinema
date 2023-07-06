package org.sadkowski.cinema.domain.shows.ports.infrastructure.write;

import org.sadkowski.cinema.domain.shows.model.Movie;

import java.util.Optional;
import java.util.UUID;

public interface MovieWriteRepository {
    void save(Movie movie);
}
