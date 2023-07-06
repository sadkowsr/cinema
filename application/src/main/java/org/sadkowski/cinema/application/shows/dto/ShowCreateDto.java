package org.sadkowski.cinema.application.shows.dto;

import jakarta.validation.constraints.NotBlank;
import org.sadkowski.cinema.domain.shows.model.ShowTypeDto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ShowCreateDto(
        @NotBlank(message = "Room id cannot be blank")
        UUID roomId,
        @NotBlank(message = "Movie id cannot be blank")
        UUID movieId,

        @NotBlank(message = "Planner id cannot be blank")
        UUID planerId,
        @NotBlank(message = "Start cannot be blank")
        LocalDateTime start,

        @NotBlank(message = "ShowTypeDto cannot be blank")
        ShowTypeDto showType,

        LocalDateTime optionalEnd
) {


}
