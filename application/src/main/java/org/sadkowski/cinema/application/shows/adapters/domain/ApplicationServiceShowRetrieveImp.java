package org.sadkowski.cinema.application.shows.adapters.domain;

import lombok.RequiredArgsConstructor;
import org.sadkowski.cinema.domain.shows.ports.application.ApplicationServiceShowRetrieve;
import org.sadkowski.cinema.domain.shows.model.Show;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.read.ShowReadRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ApplicationServiceShowRetrieveImp implements ApplicationServiceShowRetrieve {

    private final ShowReadRepository showReadRepository;

    public List<Show> getShows(LocalDateTime start){
       return showReadRepository.getShows(start);
    }
}
