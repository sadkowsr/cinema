package org.sadkowski.cinema.domain.shows.appservices.adapter;

import org.sadkowski.cinema.domain.shows.model.Show;
import org.sadkowski.cinema.domain.shows.ports.application.command.ShowCommandRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ShowCommandRepositoryImp implements ShowCommandRepository {
    @Override
    public void save(Show show) {
        //TODO sadkowsr
    }

    @Override
    public void delete(UUID showId) {

    }
}
