package com.metro.util;

import com.metro.graph.Station;

import java.util.List;

/**
 * Утилитный класс для работы с маршрутами
 */
public class RouteUtils {

    private RouteUtils() {
    }

    /**
     * Подсчитывает количество пересадок в маршруте
     * @param path список станций маршрута
     * @return количество пересадок
     */
    public static int countTransfers(List<Station> path) {
        if (path == null || path.isEmpty()) {
            return 0;
        }

        int transfers = 0;
        String currentLine = path.get(0).getLine();

        for (Station station : path) {
            if (!station.getLine().equals(currentLine)) {
                transfers++;
                currentLine = station.getLine();
            }
        }
        return transfers;
    }

    /**
     * Форматирует маршрут с указанием пересадок
     * @param path список станций маршрута
     * @return отформатированная строка маршрута
     */
    public static String formatRouteWithTransfers(List<Station> path) {
        if (path == null || path.isEmpty()) {
            return "Маршрут пуст";
        }

        StringBuilder sb = new StringBuilder();
        String currentLine = path.get(0).getLine();
        int step = 1;

        for (Station station : path) {
            if (!station.getLine().equals(currentLine)) {
                sb.append("   └─ ПЕРЕСАДКА НА ").append(station.getLine()).append("\n");
                currentLine = station.getLine();
            }
            sb.append(String.format("%2d. %s\n", step++, station.getName()));
        }

        return sb.toString();
    }

    /**
     * Получает детальную информацию о маршруте
     * @param path список станций маршрута
     * @return информация о маршруте в виде строки
     */
    public static String getRouteDetails(List<Station> path, int totalTime) {
        if (path == null || path.isEmpty()) {
            return "Нет информации о маршруте";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=".repeat(50)).append("\n");
        sb.append("МАРШРУТ НАЙДЕН\n");
        sb.append("=".repeat(50)).append("\n\n");

        sb.append(String.format("Количество станций: %d\n", path.size()));
        sb.append(String.format("Пересадок: %d\n", countTransfers(path)));
        sb.append(String.format("Общее время: %d минут\n\n", totalTime));
        sb.append("Детальный маршрут:\n");

        sb.append(formatRouteWithTransfers(path));
        sb.append("\n").append("=".repeat(50));

        return sb.toString();
    }
}