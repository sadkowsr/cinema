package org.sadkowski.cinema.domain.shows.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.hash;

@Builder
public record Room(

        UUID roomId,
        String name,
        Long cleanRoomDurationMinutes
) {

    @Override
    public int hashCode() {
        return hash(roomId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room room)) {
            return false;
        }
        return Objects.equals(this.roomId, room.roomId());
    }

}
