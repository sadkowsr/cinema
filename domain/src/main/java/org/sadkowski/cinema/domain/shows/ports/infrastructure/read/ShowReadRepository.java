package org.sadkowski.cinema.domain.shows.ports.infrastructure.read;

import org.sadkowski.cinema.domain.shows.model.Show;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShowReadRepository {
    boolean intersect(UUID roomId, LocalDateTime start, LocalDateTime end);

    List<Show> getShows(LocalDateTime start);

    Optional<Show> findByShowId(UUID showId);
}
