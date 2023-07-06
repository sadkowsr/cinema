package org.sadkowski.cinema.domain.shows.model;

import lombok.Builder;

import java.util.Objects;
import java.util.UUID;

@Builder
public record Planner(

        UUID plannerId,
        String name,
        String surname) {

    @Override
    public int hashCode() {
        return Objects.hash(plannerId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Planner planner)) {
            return false;
        }
        return Objects.equals(plannerId, planner.plannerId);
    }

}
