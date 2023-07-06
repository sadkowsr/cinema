package org.sadkowski.cinema.domain.shows.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sadkowski.cinema.domain.shows.model.ShowTypeDto;
import org.sadkowski.cinema.domain.shows.model.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.UUID;

import static java.lang.Boolean.TRUE;
import static java.util.Objects.hash;
import static org.junit.jupiter.api.Assertions.*;

class ShowTest {

    UUID showId = UUID.randomUUID();
    UUID showId2 = UUID.randomUUID();
    private static final UUID MOVIE_ID = UUID.randomUUID();
    private static final UUID ROOM_ID = UUID.randomUUID();
    private static final UUID PLANNER_ID = UUID.randomUUID();
    private static final Movie MOVIE = new Movie(MOVIE_ID, "title", 1L, TRUE);
    private static final Room ROOM = new Room(ROOM_ID, "name", 1L);
    private static final Planner PLANNER = new Planner(PLANNER_ID, "name", "surname");
    private static final AvailableHours AVAILABLE_HOURS = new AvailableHours(ShowTypeDto.PREMIERE,
            LocalTime.of(20, 48, 36), LocalTime.of(20, 48, 36));

    Show show = new Show(showId, MOVIE, ROOM, PLANNER, AVAILABLE_HOURS,
            LocalDateTime.of(2023, Month.JANUARY, 1, 0, 0, 0),
            LocalDateTime.of(2023, Month.JULY, 2, 1, 1, 1),
            1L, ShowTypeDto.PREMIERE);
     Show showWithShowIdNull = new Show(null, MOVIE, ROOM, PLANNER, AVAILABLE_HOURS,
             LocalDateTime.of(2023, Month.JANUARY, 1, 0, 0, 0),
             LocalDateTime.of(2023, Month.JULY, 2, 1, 1, 1),
             1L, ShowTypeDto.PREMIERE);
     Show showWithDifferentShowId = new Show(showId2, MOVIE, ROOM, PLANNER, AVAILABLE_HOURS,
             LocalDateTime.of(2023, Month.JANUARY, 1, 0, 0, 0),
             LocalDateTime.of(2023, Month.JULY, 2, 1, 1, 1),
             1L, ShowTypeDto.PREMIERE);
     Show showWithTheSameShowId = new Show(showId, MOVIE, ROOM, PLANNER, AVAILABLE_HOURS,
             LocalDateTime.of(2023, Month.JANUARY, 1, 0, 0, 0),
             LocalDateTime.of(2023, Month.JULY, 2, 1, 1, 1),
             1L, ShowTypeDto.PREMIERE);

    @Test
    void testHashCode() {
        int result = show.hashCode();
        Assertions.assertEquals(hash(showId), result);
    }

    @Test
    void testEquals() {
        assertTrue(show.equals(show));
        assertFalse(show.equals(null));
        assertFalse(show.equals("o"));
        assertFalse(show.equals(new Object()));
        assertFalse(show.equals(showWithShowIdNull));
        assertFalse(show.equals(showWithDifferentShowId));
        assertTrue(show.equals(showWithTheSameShowId));
    }

    @Test
    void testShowId() {
        UUID result = show.showId();
        Assertions.assertEquals(showId, result);
    }

    @Test
    void testMovie() {
        Movie result = show.movie();
        Assertions.assertEquals(new Movie(MOVIE_ID, "title", 1L, TRUE), result);
    }

    @Test
    void testRoom() {
        Room result = show.room();
        Assertions.assertEquals(new Room(ROOM_ID, "name", 1L), result);
    }

    @Test
    void testPlanner() {
        Planner result = show.planner();
        Assertions.assertEquals(new Planner(PLANNER_ID, "name", "surname"), result);
    }

    @Test
    void testAvailableHours() {
        AvailableHours result = show.availableHours();
        Assertions.assertEquals(new AvailableHours(ShowTypeDto.PREMIERE, LocalTime.of(20, 48, 36), LocalTime.of(20, 48, 36)), result);
    }

    @Test
    void testStart() {
        LocalDateTime result = show.start();
        Assertions.assertEquals(LocalDateTime.of(2023, Month.JANUARY, 1, 0, 0, 0), result);
    }

    @Test
    void testEnd() {
        LocalDateTime result = show.end();
        Assertions.assertEquals(LocalDateTime.of(2023, Month.JULY, 2, 1, 1, 1), result);
    }

    @Test
    void testCleanRoomDurationMinutes() {
        Long result = show.cleanRoomDurationMinutes();
        Assertions.assertEquals(Long.valueOf(1), result);
    }

    @Test
    void testShowType() {
        ShowTypeDto result = show.showType();
        Assertions.assertEquals(ShowTypeDto.PREMIERE, result);
    }

    @Test
    void testBuilder() {
        Show.ShowBuilder result = Show.builder();
        assertNotNull(result);
    }
}