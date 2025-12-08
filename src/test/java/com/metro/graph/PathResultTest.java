package com.metro.graph;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для результата расчета пути
 */
class PathResultTest {

    @Test
    void testPathResultCreation() {
        Station station1 = new Station("1", "Станция A", "Линия");
        Station station2 = new Station("2", "Станция B", "Линия");
        List<Station> path = Arrays.asList(station1, station2);
        PathResult result = new PathResult(path, 10);
        assertNotNull(result.getPath());
        assertEquals(2, result.getPath().size());
        assertEquals(10, result.getTotalTime());
        assertEquals(station1, result.getPath().get(0));
        assertEquals(station2, result.getPath().get(1));
    }

    @Test
    void testPathResultToString() {
        Station station1 = new Station("1", "Станция A", "Линия");
        Station station2 = new Station("2", "Станция B", "Линия");
        List<Station> path = Arrays.asList(station1, station2);
        PathResult result = new PathResult(path, 15);
        String toString = result.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("15"));
        assertTrue(toString.contains("Станция A"));
    }
}