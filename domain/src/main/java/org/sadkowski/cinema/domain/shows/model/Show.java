package org.sadkowski.cinema.domain.shows.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.hash;

@Builder
public record Show(

        UUID showId,
        Movie movie,
        Room room,
        Planner planner,
        AvailableHours availableHours,
        LocalDateTime start,
        LocalDateTime end,
        Long cleanRoomDurationMinutes,
        ShowTypeDto showType
) {
    @Override
    public int hashCode() {
        return hash(showId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Show show)) {
            return false;
        }
        return Objects.equals(showId, show.showId());
    }
}
