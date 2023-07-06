package org.sadkowski.cinema.domain.shows.appservices.adapter;

import lombok.RequiredArgsConstructor;
import org.sadkowski.cinema.domain.shows.model.Show;
import org.sadkowski.cinema.domain.shows.ports.application.query.ShowQueryRepository;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.read.ShowReadRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ShowQueryRepositoryImp implements ShowQueryRepository {

    private final ShowReadRepository showReadRepository;

    @Override
    public boolean intersect(UUID uuid, LocalDateTime start, LocalDateTime end) {
        return false;
    }

    @Override
    public List<Show> getShows(LocalDateTime start) {
        return null;
    }

    @Override
    public Optional<Show> findByShowId(UUID showId) {
        return Optional.empty();
    }
}
