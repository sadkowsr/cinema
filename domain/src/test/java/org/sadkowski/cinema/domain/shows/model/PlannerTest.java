package org.sadkowski.cinema.domain.shows.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sadkowski.cinema.domain.shows.model.Planner;

import java.util.UUID;

import static java.util.Objects.hash;
import static org.junit.jupiter.api.Assertions.*;

class PlannerTest {
    UUID plannerId = UUID.randomUUID();
    UUID plannerId2 = UUID.randomUUID();
    Planner planner = new Planner(plannerId, "name", "surname");
    Planner plannerWithNullPlannerId = new Planner(null, "name", "surname");
    Planner plannerWithDifferentPlannerId = new Planner(plannerId2, "name", "surname");
    Planner plannerWithTheSamePlannerId = new Planner(plannerId, "", "");
    @Test
    void testHashCode() {
        int result = planner.hashCode();
        assertEquals(hash(plannerId), result);
    }

    @Test
    void testEquals() {
        assertTrue(planner.equals(planner));
        assertFalse(planner.equals(null));
        assertFalse(planner.equals("o"));
        assertFalse(planner.equals(new Object()));
        assertFalse(planner.equals(plannerWithNullPlannerId));
        assertFalse(planner.equals(plannerWithDifferentPlannerId));
        assertTrue(planner.equals(plannerWithTheSamePlannerId));
    }

    @Test
    void testPlannerId() {
        UUID result = planner.plannerId();
         assertEquals(plannerId, result);
    }

    @Test
    void testName() {
        String result = planner.name();
        assertEquals("name", result);
    }

    @Test
    void testSurname() {
        String result = planner.surname();
        assertEquals("surname", result);
    }

    @Test
    void testBuilder() {
        Planner.PlannerBuilder result = Planner.builder();
        assertNotNull(result);
    }
}
