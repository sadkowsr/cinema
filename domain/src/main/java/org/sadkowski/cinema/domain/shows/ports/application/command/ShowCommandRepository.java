package org.sadkowski.cinema.domain.shows.ports.application.command;

import org.sadkowski.cinema.domain.shows.model.Show;

import java.util.UUID;

public interface ShowCommandRepository {
    void save(Show show);

    void delete(UUID showId);
}
