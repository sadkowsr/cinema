package org.sadkowski.cinema.domain.shows.ports.application;

import org.sadkowski.cinema.domain.shows.model.Show;

import java.time.LocalDateTime;
import java.util.List;

public interface ApplicationServiceShowRetrieve {

    List<Show> getShows(LocalDateTime start);
}
