package org.sadkowski.cinema.domain.shows.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sadkowski.cinema.domain.shows.model.Room;

import java.util.UUID;

import static java.util.Objects.hash;
import static org.junit.jupiter.api.Assertions.*;

class RoomTest {
   UUID roomId = UUID.randomUUID();
    UUID roomId2 = UUID.randomUUID();
    Room room = new Room(roomId, "name", 1L);
    Room roomWithRoomIdNull = new Room(null, "name", 1L);
    Room roomWithDifferentRoomId = new Room(roomId2, "name", 1L);

    Room roomWithTheSameRoomId = new Room(roomId, "", 1L);

    @Test
    void testHashCode() {
        int result = room.hashCode();
        Assertions.assertEquals(hash(roomId), result);
    }

    @Test
    void testEquals() {
        assertTrue(room.equals(room));
        assertFalse(room.equals(null));
        assertFalse(room.equals("o"));
        assertFalse(room.equals(new Object()));
        assertFalse(room.equals(roomWithRoomIdNull));
        assertFalse(room.equals(roomWithDifferentRoomId));
        assertTrue(room.equals(roomWithTheSameRoomId));
    }

    @Test
    void testRoomId() {
        UUID result = room.roomId();
        Assertions.assertEquals(roomId, result);
    }

    @Test
    void testName() {
        String result = room.name();
        Assertions.assertEquals("name", result);
    }

    @Test
    void testCleanRoomDurationMinutes() {
        Long result = room.cleanRoomDurationMinutes();
        Assertions.assertEquals(Long.valueOf(1), result);
    }


    @Test
    void testBuilder() {
        Room.RoomBuilder result = Room.builder();
        assertNotNull(result);
    }
}
