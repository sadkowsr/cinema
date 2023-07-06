package org.sadkowski.cinema.domain.shows.ports.infrastructure.write;

import org.sadkowski.cinema.domain.shows.model.Room;

public interface RoomWriteRepository {
    void save(Room room);
}
