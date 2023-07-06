package org.sadkowski.cinema.domain.shows.model;

import lombok.Builder;

import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.hash;

@Builder
public record Movie(

        UUID movieId,
        String title,
        Long durationMovieInMinutes,
        Boolean glasses3DRequired) {

    @Override
    public int hashCode() {
        return hash(movieId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie movie)) {
            return false;
        }
        return Objects.equals(this.movieId, movie.movieId);
    }

}
