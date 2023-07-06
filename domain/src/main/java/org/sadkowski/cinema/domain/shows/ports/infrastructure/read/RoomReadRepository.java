package org.sadkowski.cinema.domain.shows.ports.infrastructure.read;

import org.sadkowski.cinema.domain.shows.model.Room;

import java.util.Optional;
import java.util.UUID;

public interface RoomReadRepository {
    Optional<Room> findById(UUID roomId);
}
