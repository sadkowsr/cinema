package org.sadkowski.cinema.application.shows.adapters;

import lombok.RequiredArgsConstructor;
import org.sadkowski.cinema.domain.shows.ports.application.query.ShowQueryRepository;
import org.sadkowski.cinema.domain.shows.model.Show;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ApplicationServiceShowRetrieve {

    private final ShowQueryRepository repository;

    public List<Show> getShows(LocalDateTime start){
       return repository.getShows(start);
    }
}
