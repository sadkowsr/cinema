package org.sadkowski.cinema.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CinemaApplicationTest {

    @Test
    void testMain() {
        assertDoesNotThrow(() -> CinemaApplication.main(new String[]{"args"}));
    }
}