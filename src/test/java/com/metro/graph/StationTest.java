package com.metro.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса Station
 */
class StationTest {

    @Test
    void testStationCreation() {
        Station station = new Station("1", "Автово", "Кировско-Выборгская");

        assertEquals("1", station.getId());
        assertEquals("Автово", station.getName());
        assertEquals("Кировско-Выборгская", station.getLine());
    }

    @Test
    void testStationEqualsAndHashCode() {
        Station station1 = new Station("1", "Станция", "Линия");
        Station station2 = new Station("1", "Станция", "Линия");
        Station station3 = new Station("2", "Станция", "Линия");

        assertEquals(station1, station2);
        assertNotEquals(station1, station3);
        assertEquals(station1.hashCode(), station2.hashCode());
    }
}