package org.sadkowski.cinema.domain.shows.model;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.sadkowski.cinema.domain.shows.model.ShowTypeDto.PREMIERE;

class AvailableHoursTest {
       AvailableHours availableHours = new AvailableHours(PREMIERE, LocalTime.of(17, 50, 45), LocalTime.of(17, 50, 45));

    @Test
    void testHashCode() {
        int result = availableHours.hashCode();
            assertEquals(0,  result);
    }

    @Test
    void testEquals() {
        assertTrue(availableHours.equals(availableHours));
        assertFalse(availableHours.equals(null));
        assertFalse(availableHours.equals("o"));
        assertFalse(availableHours.equals(new Object()));
        assertFalse(availableHours.equals(new AvailableHours(null, LocalTime.of(17, 50, 45), LocalTime.of(17, 50, 45))));
        assertFalse(availableHours.equals(new AvailableHours(ShowTypeDto.REGULAR, LocalTime.of(17, 50, 45), LocalTime.of(17, 50, 45))));
        assertTrue(availableHours.equals(new AvailableHours(PREMIERE, LocalTime.of(17, 50, 45), LocalTime.of(17, 50, 45))));
    }

    @Test
    void testShowType() {
        ShowTypeDto result = availableHours.showType();
        assertEquals(PREMIERE, result);
    }

    @Test
    void testStart() {
        LocalTime result = availableHours.start();
        assertEquals(LocalTime.of(17, 50, 45), result);
    }

    @Test
    void testEnd() {
        LocalTime result = availableHours.end();
        assertEquals(LocalTime.of(17, 50, 45), result);
    }

    @Test
    void testBuilder() {
        AvailableHours.AvailableHoursBuilder result = AvailableHours.builder();
        assertNotNull(result);
    }

}