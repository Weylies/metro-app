package com.metro.algorithm;

import com.metro.graph.Graph;
import com.metro.graph.PathResult;
import com.metro.graph.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для алгоритма Дейкстры
 */
class DijkstraAlgorithmTest {
    private Graph graph;
    private DijkstraAlgorithm algorithm;

    @BeforeEach
    void setUp() {
        graph = new Graph();
        algorithm = new DijkstraAlgorithm();
    }

    @Test
    void testFindShortestPath_SameStation() {
        Station station = new Station("1", "Тестовая", "Линия");
        graph.addStation(station);

        PathResult result = algorithm.findShortestPath(graph, station, station);

        assertNotNull(result);
        assertEquals(1, result.getPath().size());
        assertEquals(0, result.getTotalTime());
        assertEquals(station, result.getPath().get(0));
    }

    @Test
    void testFindShortestPath_DirectConnection() {
        Station station1 = new Station("1", "Станция 1", "Линия");
        Station station2 = new Station("2", "Станция 2", "Линия");

        graph.addStation(station1);
        graph.addStation(station2);
        graph.addEdge(station1, station2, 5);

        PathResult result = algorithm.findShortestPath(graph, station1, station2);

        assertNotNull(result);
        List<Station> path = result.getPath();
        assertEquals(2, path.size());
        assertEquals(station1, path.get(0));
        assertEquals(station2, path.get(1));
        assertEquals(5, result.getTotalTime());
    }

    @Test
    void testFindShortestPath_NoPath() {
        Station station1 = new Station("1", "Станция 1", "Линия");
        Station station2 = new Station("2", "Станция 2", "Линия");
        Station station3 = new Station("3", "Станция 3", "Линия");

        graph.addStation(station1);
        graph.addStation(station2);
        graph.addStation(station3);
        graph.addEdge(station1, station2, 5);

        PathResult result = algorithm.findShortestPath(graph, station1, station3);

        assertNull(result);
    }

    @Test
    void testFindShortestPath_ThroughIntermediate() {
        Station station1 = new Station("1", "Станция 1", "Линия");
        Station station2 = new Station("2", "Станция 2", "Линия");
        Station station3 = new Station("3", "Станция 3", "Линия");

        graph.addStation(station1);
        graph.addStation(station2);
        graph.addStation(station3);
        graph.addEdge(station1, station2, 3);
        graph.addEdge(station2, station3, 4);

        PathResult result = algorithm.findShortestPath(graph, station1, station3);

        assertNotNull(result);
        assertEquals(3, result.getPath().size());
        assertEquals(7, result.getTotalTime());
        assertEquals(station1, result.getPath().get(0));
        assertEquals(station2, result.getPath().get(1));
        assertEquals(station3, result.getPath().get(2));
    }
}