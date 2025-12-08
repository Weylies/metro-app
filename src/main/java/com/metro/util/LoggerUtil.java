package com.metro.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Утилита для работы с логгером
 */
public class LoggerUtil {

    private LoggerUtil() {
    }

    /**
     * Получает логгер для класса
     * @return экземпляр логгера
     */
    public static Logger getLogger() {
        return LogManager.getLogger(getCallerClass());
    }

    /**
     * Определяет класс, который вызвал метод getLogger
     * @return класс-вызыватель
     */
    private static Class<?> getCallerClass() {
        try {
            return Class.forName(Thread.currentThread().getStackTrace()[3].getClassName());
        } catch (ClassNotFoundException e) {
            return LoggerUtil.class;
        }
    }
}