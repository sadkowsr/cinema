package org.sadkowski.cinema.application.shows.handlers;

import lombok.RequiredArgsConstructor;
import org.sadkowski.cinema.application.shows.dto.ShowCreateDto;
import org.sadkowski.cinema.domain.shows.ports.application.ApplicationServiceShowAdd;
import org.sadkowski.cinema.domain.shows.appservices.commands.AddShowCommand;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ShowCommandHandler {

    private final ApplicationServiceShowAdd appService;

    public UUID handle(ShowCreateDto show) {
        UUID showId = UUID.randomUUID();
        appService.addShow(buildAddShowCommand(show, showId));
        return showId;
    }
    public static AddShowCommand buildAddShowCommand(ShowCreateDto show, UUID showId) {
        return AddShowCommand.builder()
                .roomId(show.roomId())
                .movieId(show.movieId())
                .plannerId(show.planerId())
                .showType(show.showType())
                .start(show.start())
                .optionalEnd(show.optionalEnd())
                .showId(showId)
                .build();
    }
}
