package org.sadkowski.cinema.domain.shows.ports.application.query;

import org.sadkowski.cinema.domain.shows.model.Room;

import java.util.Optional;
import java.util.UUID;

public interface RoomQueryRepository {
    Optional<Room> findById(UUID uuid);
}
