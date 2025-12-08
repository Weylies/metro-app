package com.metro.fileio;

import com.metro.graph.Graph;
import com.metro.graph.Station;
import com.metro.util.LoggerUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Класс для загрузки данных о метрополитене из файлов
 */
public class MetroDataLoader {
    private static final org.apache.logging.log4j.Logger logger = LoggerUtil.getLogger();

    /**
     * Загружает данные о станциях и создает граф
     * @return граф метрополитена
     */
    public Graph loadMetroData() {
        logger.info("Начало загрузки данных метрополитена");
        Graph graph = new Graph();

        try {
            loadStations(graph);
            loadConnections(graph);
            logger.info("Данные метрополитена успешно загружены. Всего станций: {}",
                    graph.getAllStations().size());
        } catch (Exception e) {
            logger.error("Ошибка при загрузке данных метрополитена: {}", e.getMessage());
            throw new RuntimeException("Не удалось загрузить данные метрополитена", e);
        }

        return graph;
    }

    /**
     * Загружает станции из файла
     * @param graph граф для добавления станций
     */
    private void loadStations(Graph graph) {
        try (InputStream inputStream = getClass().getResourceAsStream("/stations.txt");
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String line;
            int stationCount = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Station station = new Station(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim()
                    );
                    graph.addStation(station);
                    stationCount++;
                }
            }
            logger.debug("Загружено {} станций", stationCount);
        } catch (Exception e) {
            logger.error("Ошибка при загрузке станций: {}", e.getMessage());
            throw new RuntimeException("Не удалось загрузить станции", e);
        }
    }

    /**
     * Загружает соединения между станциями
     * @param graph граф для добавления соединений
     */
    private void loadConnections(Graph graph) {
        try (InputStream inputStream = getClass().getResourceAsStream("/connections.txt");
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String line;
            int connectionCount = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Station from = graph.getStationById(parts[0].trim());
                    Station to = graph.getStationById(parts[1].trim());
                    int time = Integer.parseInt(parts[2].trim());

                    if (from != null && to != null) {
                        graph.addEdge(from, to, time);
                        connectionCount++;
                    } else {
                        logger.warn("Не удалось найти станцию для соединения: {} - {}",
                                parts[0].trim(), parts[1].trim());
                    }
                }
            }
            logger.debug("Загружено {} соединений", connectionCount);
        } catch (Exception e) {
            logger.error("Ошибка при загрузке соединений: {}", e.getMessage());
            throw new RuntimeException("Не удалось загрузить соединения", e);
        }
    }
}