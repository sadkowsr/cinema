package org.sadkowski.cinema.domain.shows.ports.application.command;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ShowQueryRepository {
    boolean intersect(UUID uuid, LocalDateTime start, LocalDateTime end);
}
