package com.metro.graph;

import java.util.*;

/**
 * Класс графа для представления метрополитена
 */
public class Graph {
    private final Map<Station, List<Edge>> adjacencyList;
    private final Map<String, Station> stations;

    public Graph() {
        this.adjacencyList = new HashMap<>();
        this.stations = new HashMap<>();
    }

    /**
     * Добавляет станцию в граф
     * @param station станция для добавления
     */
    public void addStation(Station station) {
        adjacencyList.putIfAbsent(station, new ArrayList<>());
        stations.put(station.getId(), station);
    }

    /**
     * Добавляет ребро между двумя станциями
     * @param from исходная станция
     * @param to конечная станция
     * @param time время перемещения в минутах
     */
    public void addEdge(Station from, Station to, int time) {
        adjacencyList.get(from).add(new Edge(to, time));
        adjacencyList.get(to).add(new Edge(from, time));
    }

    /**
     * Получает список смежных станций
     * @param station станция для получения соседей
     * @return список ребер к соседним станциям
     */
    public List<Edge> getNeighbors(Station station) {
        return adjacencyList.getOrDefault(station, new ArrayList<>());
    }

    /**
     * Получает станцию по идентификатору
     * @param id идентификатор станции
     * @return станция или null если не найдена
     */
    public Station getStationById(String id) {
        return stations.get(id);
    }

    /**
     * Получает все станции графа
     * @return множество всех станций
     */
    public Set<Station> getAllStations() {
        return adjacencyList.keySet();
    }

    /**
     * Поиск станции по названию
     * @param name название станции
     * @return Optional со станцией если найдена
     */
    public Optional<Station> findStationByName(String name) {
        return adjacencyList.keySet().stream()
                .filter(station -> station.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Получает список всех станций отсортированных по номеру (ID)
     * @return отсортированный список станций
     */
    public List<Station> getSortedStations() {
        return adjacencyList.keySet().stream()
                .sorted(Comparator.comparing(station -> Integer.parseInt(station.getId())))
                .toList();
    }

    /**
     * Внутренний класс для представления ребра графа
     */
    public static class Edge {
        private final Station target;
        private final int time;

        public Edge(Station target, int time) {
            this.target = target;
            this.time = time;
        }

        public Station getTarget() {
            return target;
        }

        public int getTime() {
            return time;
        }
    }
}