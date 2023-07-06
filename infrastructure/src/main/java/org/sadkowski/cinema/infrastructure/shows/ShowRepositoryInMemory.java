package org.sadkowski.cinema.infrastructure.shows;

import lombok.extern.slf4j.Slf4j;
import org.sadkowski.cinema.domain.shows.model.Show;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.read.ShowReadRepository;
import org.sadkowski.cinema.domain.shows.ports.infrastructure.write.ShowWriteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static java.time.LocalTime.MIN;

@Slf4j
@Service
public class ShowRepositoryInMemory implements ShowWriteRepository, ShowReadRepository {

    public static final int DAYS_DURATION_FOR_RETRIEVE_SHOWS = 7;
    private final Map<UUID, Show> map;

    public ShowRepositoryInMemory() {
        this.map = new HashMap<>();
    }

    @Override
    public void save(Show command) {
        log.debug("Saving show: {}", command);
        map.put(command.showId(), command);
        log.info("Saved show: {}", command);
    }

    @Override
    public boolean delete(UUID showId) {
        Show show = map.remove(showId);
        boolean result = Optional.ofNullable(show).stream().anyMatch(s -> true);
        if (result) {
            log.info("Deleted show: {}", show);

        }
        return result;
    }

        @Override
        public boolean intersect (UUID roomId, LocalDateTime start, LocalDateTime end){
            boolean intersect = map.values().stream()
                    .filter(show -> show.room().roomId().equals(roomId))
                    .filter(show -> show.start().isBefore(end) || show.start().equals(start))
                    .filter(show -> start.isBefore(show.end()) || show.end().equals(end))
                    .anyMatch(show -> true);
            log.info("Checking if show intersects: {} {} {}: {}", roomId, start, end, intersect);
            return intersect;
        }

        @Override
        public List<Show> getShows(LocalDateTime start){
            LocalDateTime beginningOfDayStart = start.with(MIN); //let start from the beginning of the day
            //end of the day is beginningOfDayStart + duration days
            return getAllShows(beginningOfDayStart, beginningOfDayStart.plusDays(DAYS_DURATION_FOR_RETRIEVE_SHOWS));
        }

    @Override
    public Optional<Show> findByShowId(UUID showId) {
        return Optional.of(map.get(showId));
    }

    private List<Show> getAllShows (LocalDateTime start, LocalDateTime end){
            log.debug("Getting all shows: {}", start);
            List<Show> shows = map.values().stream()
                    .filter(show -> show.start().isAfter(start))
                    .filter(show -> show.end().isBefore(end))
                    .toList();
            log.info("Retrieved all shows: {}", start);
            return shows;
        }

    }
