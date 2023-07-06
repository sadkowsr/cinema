package org.sadkowski.cinema.domain.shows.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static java.util.Objects.hash;
import static org.junit.jupiter.api.Assertions.*;

class MovieTest {
    UUID movieID = UUID.randomUUID();
    UUID movieID2 = UUID.randomUUID();
    Movie movie = new Movie(movieID, "title", 1L, Boolean.TRUE);
    Movie movieWithNullMovieId = new Movie(null, "title", 1L, Boolean.TRUE);
    Movie movieWithDifferentMovieIdAsMovieButTheSameFields = new Movie(movieID2, "title", 1L, Boolean.TRUE);
    Movie movieWithTheSameMovieIdAsMovie = new Movie(movieID, "title", 1L, Boolean.TRUE);

    @Test
    void testHashCode() {
        int result = movie.hashCode();
        assertEquals(hash(movieID), result);
    }

    @Test
    void testEquals() {
        assertTrue(movie.equals(movie));
        assertFalse(movie.equals(null));
        assertFalse(movie.equals("o"));
        assertFalse(movie.equals(new Object()));
        assertFalse(movie.equals(movieWithNullMovieId));
        assertFalse(movie.equals(movieWithDifferentMovieIdAsMovieButTheSameFields));
        assertTrue(movie.equals(movieWithTheSameMovieIdAsMovie));
    }

    @Test
    void testMovieId() {
        UUID result = movie.movieId();
        assertEquals(this.movieID, result);
    }

    @Test
    void testTitle() {
        String result = movie.title();
        assertEquals("title", result);
    }

    @Test
    void testDurationMovieInMinutes() {
        Long result = movie.durationMovieInMinutes();
        assertEquals(Long.valueOf(1), result);
    }

    @Test
    void testGlasses3DRequired() {
        Boolean result = movie.glasses3DRequired();
        assertEquals(Boolean.TRUE, result);
    }

    @Test
    void testBuilder() {
        Movie.MovieBuilder result = Movie.builder();
        assertNotNull(result);
    }
}
