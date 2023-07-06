package org.sadkowski.cinema.domain.shows.appservices.commands;

import lombok.Builder;
import org.sadkowski.cinema.domain.shows.model.ShowTypeDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AddShowCommand(

        UUID roomId,
        UUID movieId,
        UUID plannerId,
        LocalDateTime start,
        ShowTypeDto showType,
        LocalDateTime optionalEnd,
        UUID showId

) {

}
