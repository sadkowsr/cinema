package org.sadkowski.cinema.domain.shows.ports.infrastructure.write;

import org.sadkowski.cinema.domain.shows.model.Movie;

public interface MovieWriteRepository {
    void save(Movie movie);
}
