package com.metro.util;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для утилиты логирования
 */
class LoggerUtilsTest {

    @Test
    void testGetLogger() {
        Logger logger = LoggerUtil.getLogger();
        assertNotNull(logger, "Логгер должен быть создан");
        assertTrue(logger instanceof Logger, "Должен возвращаться Logger");
    }

    @Test
    void testLoggerName() {
        Logger logger = LoggerUtil.getLogger();
        assertEquals(LoggerUtilsTest.class.getName(), logger.getName(),
                "Имя логгера должно соответствовать тестовому классу");
    }
}