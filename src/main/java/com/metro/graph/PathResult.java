package com.metro.graph;

import java.util.List;

/**
 * Результат расчета пути между станциями
 */
public class PathResult {
    private final List<Station> path;
    private final int totalTime;

    public PathResult(List<Station> path, int totalTime) {
        this.path = path;
        this.totalTime = totalTime;
    }

    public List<Station> getPath() {
        return path;
    }

    public int getTotalTime() {
        return totalTime;
    }

    @Override
    public String toString() {
        return "Путь: " + path + "\nОбщее время: " + totalTime + " минут";
    }
}