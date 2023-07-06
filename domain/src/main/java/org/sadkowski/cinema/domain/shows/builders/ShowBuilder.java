package org.sadkowski.cinema.domain.shows.builders;

import org.sadkowski.cinema.domain.shows.appservices.commands.AddShowCommand;
import org.sadkowski.cinema.domain.shows.model.*;

import java.time.LocalDateTime;
import java.util.Optional;

public class ShowBuilder {

    private ShowBuilder(){

    }
    public static Show showOf(AddShowCommand command,
                              Optional<Planner> planner,
                              Optional<Movie> movie,
                              Optional<Room> room,
                              Optional<AvailableHours> availableHours
    ) {

        LocalDateTime endTime = Optional.ofNullable(command.optionalEnd()).orElse(
                command.start().plusMinutes(movie.orElse(Movie.builder().build()).durationMovieInMinutes()));

        return Show.builder()
                .showId(command.showId())
                .movie(movie.orElse(null))
                .room(room.orElse(null))
                .planner(planner.orElse(null))
                .start(command.start())
                .end(endTime)
                .cleanRoomDurationMinutes(room.stream()
                        .map(Room::cleanRoomDurationMinutes)
                        .findFirst()
                        .orElse(null))
                .availableHours(availableHours.orElse(null))
                .showType(command.showType())
                .build();
    }
}
