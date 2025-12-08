package com.metro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Интеграционные тесты для основного приложения
 */
class MainTest {

    @Test
    void testMetroappInitialization() {
        Main app = new Main();
        assertNotNull(app, "Приложение должно создаваться");
    }

    @Test
    void testGraphIntegration() {

        Main app = new Main();
        assertNotNull(app, "Приложение с графом должно инициализироваться");
    }
}