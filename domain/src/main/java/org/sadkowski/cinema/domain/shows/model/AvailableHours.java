package org.sadkowski.cinema.domain.shows.model;

import lombok.Builder;

import java.time.LocalTime;

@Builder
public record AvailableHours(

        ShowTypeDto showType,
        LocalTime start,
        LocalTime end) {


    @Override
    public int hashCode() {
        return showType.ordinal();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if(!(o instanceof AvailableHours availableHours)) {
            return false;
        }
        return showType == availableHours.showType();
    }

}
