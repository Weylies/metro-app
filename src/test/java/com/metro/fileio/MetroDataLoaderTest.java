package com.metro.fileio;

import com.metro.graph.Graph;
import com.metro.graph.Station;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для загрузчика данных метро
 */
class MetroDataLoaderTest {

    @Test
    void testLoadMetroData() {
        MetroDataLoader loader = new MetroDataLoader();
        Graph graph = loader.loadMetroData();

        assertNotNull(graph, "Граф должен быть создан");
        assertFalse(graph.getAllStations().isEmpty(), "Должны быть загружены станции");

        assertNotNull(graph.getStationById("1"), "Должна быть станция с ID 1");
        assertNotNull(graph.getStationById("101"), "Должна быть станция с ID 101");
    }

    @Test
    void testStationsHaveCorrectData() {
        MetroDataLoader loader = new MetroDataLoader();
        Graph graph = loader.loadMetroData();
        Station station = graph.getStationById("1");

        assertNotNull(station, "Станция с ID 1 должна существовать");
        assertEquals("1", station.getId());
        assertNotNull(station.getName(), "Название станции не должно быть null");
        assertNotNull(station.getLine(), "Линия метро не должна быть null");
    }
}