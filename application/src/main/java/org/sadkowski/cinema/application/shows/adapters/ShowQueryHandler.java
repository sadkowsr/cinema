package org.sadkowski.cinema.application.shows.adapters;

import lombok.RequiredArgsConstructor;
import org.sadkowski.cinema.application.shows.dto.ShowDTO;
import org.sadkowski.cinema.domain.shows.model.Show;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static org.sadkowski.cinema.application.shows.dto.ShowDTO.showDtosOf;


@RequiredArgsConstructor
@Component
public class ShowQueryHandler {

    private final ApplicationServiceShowRetrieve appService;

    public List<ShowDTO> getShows(LocalDateTime start) {
        List<Show> shows = appService.getShows(start);
        return showDtosOf(shows);
    }

}
