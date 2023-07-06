package org.sadkowski.cinema.infrastructure.shows;

import lombok.extern.slf4j.Slf4j;
import org.sadkowski.cinema.domain.shows.model.Movie;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.read.MovieReadRepository;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.write.MovieWriteRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class MovieRepositoryInMemory implements MovieReadRepository, MovieWriteRepository {

    private final Map<UUID, Movie> movies;

    public MovieRepositoryInMemory() {
        this.movies = new HashMap<>();
    }

    @Override
    public Optional<Movie> findAllById(UUID movieId) {
        log.debug("Finding movie by id: {}", movieId);
        Optional<Movie> movie = Optional.of(movies.get(movieId));
        log.info("Found movie: {}", movie);
        return movie;
    }

    @Override
    public void save(Movie movie) {
        log.debug("Saving movie: {}", movie);
        movies.put(movie.movieId(), movie);
        log.info("Saved movie: {}", movie);
    }
}
