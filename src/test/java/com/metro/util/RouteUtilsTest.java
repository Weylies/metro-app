package com.metro.util;

import com.metro.graph.Station;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для утилиты маршрутов
 */
class RouteUtilsTest {

    @Test
    void testCountTransfers_NoTransfers() {
        List<Station> path = Arrays.asList(
                new Station("1", "А", "Красная"),
                new Station("2", "Б", "Красная")
        );
        assertEquals(0, RouteUtils.countTransfers(path));
    }

    @Test
    void testCountTransfers_OneTransfer() {
        List<Station> path = Arrays.asList(
                new Station("1", "А", "Красная"),
                new Station("101", "Б", "Синяя")
        );
        assertEquals(1, RouteUtils.countTransfers(path));
    }

    @Test
    void testCountTransfers_EmptyPath() {
        assertEquals(0, RouteUtils.countTransfers(Collections.emptyList()));
        assertEquals(0, RouteUtils.countTransfers(null));
    }

    @Test
    void testFormatRouteWithTransfers() {
        List<Station> path = Arrays.asList(
                new Station("1", "А", "Красная"),
                new Station("101", "Б", "Синяя")
        );

        String result = RouteUtils.formatRouteWithTransfers(path);

        assertNotNull(result);
        assertTrue(result.contains("А"));
        assertTrue(result.contains("Б"));
    }

    @Test
    void testGetRouteDetails() {
        List<Station> path = Arrays.asList(
                new Station("1", "А", "Красная"),
                new Station("2", "Б", "Красная")
        );

        String result = RouteUtils.getRouteDetails(path, 10);

        assertNotNull(result);
        assertTrue(result.contains("10 минут"));
    }
}