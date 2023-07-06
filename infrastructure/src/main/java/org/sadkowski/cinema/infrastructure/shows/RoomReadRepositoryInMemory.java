package org.sadkowski.cinema.infrastructure.shows;

import lombok.extern.slf4j.Slf4j;
import org.sadkowski.cinema.domain.shows.model.Room;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.read.RoomReadRepository;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.write.RoomWriteRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class RoomReadRepositoryInMemory implements RoomReadRepository, RoomWriteRepository {

    private final Map<UUID, Room> rooms;

    public RoomReadRepositoryInMemory() {
        this.rooms = new HashMap<>();
    }


    @Override
    public Optional<Room> findById(UUID roomId) {
        log.debug("Finding room by id: {}", roomId);
        Optional<Room> room = Optional.of(rooms.get(roomId));
        log.info("Found room: {}", room);
        return room;
    }

    @Override
    public void save(Room room) {
        log.debug("Saving room: {}", room);
        rooms.put(room.roomId(), room);
        log.info("Saved room: {}", room);
    }
}
