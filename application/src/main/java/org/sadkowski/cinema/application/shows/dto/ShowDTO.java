package org.sadkowski.cinema.application.shows.dto;

import lombok.Builder;
import org.sadkowski.cinema.domain.shows.model.ShowTypeDto;
import org.sadkowski.cinema.domain.shows.model.Movie;
import org.sadkowski.cinema.domain.shows.model.Show;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record ShowDTO(
        UUID showId,
        UUID roomId,
        Movie movie,
        UUID planerId,
        LocalDateTime start,
        LocalDateTime end,
        ShowTypeDto showType,

        Long durationCleaningTime
) {

    public static List<ShowDTO> showDtosOf(List<Show> shows) {
        return shows.stream()
                .map(show -> ShowDTO.builder()
                        .showId(show.showId())
                        .roomId(show.room().roomId())
                        .planerId(show.planner().plannerId())
                        .movie(show.movie())
                        .showType(show.availableHours().showType())
                        .start(show.start())
                        .end(show.end())
                        .durationCleaningTime(show.cleanRoomDurationMinutes())
                        .build())
                .toList();
    }

}
