package com.metro.graph;

/**
 * Класс, представляющий станцию метро
 */
public class Station {
    private final String id;
    private final String name;
    private final String line;

    public Station(String id, String name, String line) {
        this.id = id;
        this.name = name;
        this.line = line;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLine() {
        return line;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Station station = (Station) obj;
        return id.equals(station.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}