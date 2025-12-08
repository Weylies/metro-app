package com.metro.util;

import javafx.scene.control.Alert;

/**
 * Утилитный класс для отображения диалоговых окон
 */
public class AlertUtils {

    /**
     * Показывает информационное диалоговое окно
     * @param title заголовок окна
     * @param message сообщение
     */
    public static void showInfoAlert(String title, String message) {
        showAlert(Alert.AlertType.INFORMATION, title, message);
    }

    /**
     * Показывает предупреждающее диалоговое окно
     * @param title заголовок окна
     * @param message сообщение
     */
    public static void showWarningAlert(String title, String message) {
        showAlert(Alert.AlertType.WARNING, title, message);
    }

    /**
     * Показывает диалоговое окно с ошибкой
     * @param title заголовок окна
     * @param message сообщение
     */
    public static void showErrorAlert(String title, String message) {
        showAlert(Alert.AlertType.ERROR, title, message);
    }

    /**
     * Общий метод для отображения диалоговых окон
     * @param alertType тип диалога
     * @param title заголовок
     * @param message сообщение
     */
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}