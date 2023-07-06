package org.sadkowski.cinema.domain.shows.ports.application.query;

import org.sadkowski.cinema.domain.shows.model.Show;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShowQueryRepository {
    boolean intersect(UUID uuid, LocalDateTime start, LocalDateTime end);

    List<Show> getShows(LocalDateTime start);

    Optional<Show> findByShowId(UUID showId);

}
