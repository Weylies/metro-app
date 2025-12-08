package com.metro.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса Graph
 */
class GraphTest {
    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new Graph();
    }

    @Test
    void testAddAndGetStation() {
        Station station = new Station("1", "Тестовая", "Линия");
        graph.addStation(station);

        Station found = graph.getStationById("1");
        assertNotNull(found);
        assertEquals("Тестовая", found.getName());
    }

    @Test
    void testAddEdge() {
        Station station1 = new Station("1", "Станция 1", "Линия");
        Station station2 = new Station("2", "Станция 2", "Линия");
        graph.addStation(station1);
        graph.addStation(station2);

        graph.addEdge(station1, station2, 5);

        assertFalse(graph.getNeighbors(station1).isEmpty());
        assertEquals(5, graph.getNeighbors(station1).get(0).getTime());
    }

    @Test
    void testFindStationByName() {
        Station station = new Station("1", "Автово", "Кировско-Выборгская");
        graph.addStation(station);

        Optional<Station> found = graph.findStationByName("Автово");
        assertTrue(found.isPresent());
        assertEquals("Автово", found.get().getName());
    }
}