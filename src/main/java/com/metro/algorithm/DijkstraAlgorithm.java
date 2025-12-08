package com.metro.algorithm;

import com.metro.graph.Graph;
import com.metro.graph.PathResult;
import com.metro.graph.Station;
import com.metro.util.LoggerUtil;

import java.util.*;

/**
 * Реализация алгоритма Дейкстры для поиска кратчайшего пути
 */
public class DijkstraAlgorithm {
    private static final org.apache.logging.log4j.Logger logger = LoggerUtil.getLogger();

    /**
     * Находит кратчайший путь между двумя станциями
     * @param graph граф метрополитена
     * @param start начальная станция
     * @param end конечная станция
     * @return результат с путем и временем или null если путь не найден
     */
    public PathResult findShortestPath(Graph graph, Station start, Station end) {
        logger.info("Finding path from {} to {}", start.getName(), end.getName());


        Map<Station, Integer> distances = new HashMap<>();
        Map<Station, Station> previous = new HashMap<>();
        PriorityQueue<Station> queue = new PriorityQueue<>(
                Comparator.comparingInt(station -> distances.getOrDefault(station, Integer.MAX_VALUE))
        );

        for (Station station : graph.getAllStations()) {
            distances.put(station, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Station current = queue.poll();

            if (current.equals(end)) {
                break;
            }

            for (Graph.Edge edge : graph.getNeighbors(current)) {
                Station neighbor = edge.getTarget();
                int newDistance = distances.get(current) + edge.getTime();

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        if (distances.get(end) == Integer.MAX_VALUE) {
            logger.warn("Path from {} to {} not found", start.getName(), end.getName());
            return null;
        }

        List<Station> path = reconstructPath(previous, end);
        int totalTime = distances.get(end);

        logger.info("Path found: {} minutes through {} stations", totalTime, path.size());

        return new PathResult(path, totalTime);
    }

    /**
     * Восстанавливает путь из карты предыдущих станций
     * @param previous карта предыдущих станций
     * @param end конечная станция
     * @return список станций пути
     */
    private List<Station> reconstructPath(Map<Station, Station> previous, Station end) {
        LinkedList<Station> path = new LinkedList<>();
        Station current = end;

        while (current != null) {
            path.addFirst(current);
            current = previous.get(current);
        }

        return path;
    }
}