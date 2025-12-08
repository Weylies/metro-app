package com.metro;

import com.metro.algorithm.DijkstraAlgorithm;
import com.metro.fileio.MetroDataLoader;
import com.metro.graph.Graph;
import com.metro.graph.PathResult;
import com.metro.graph.Station;
import com.metro.util.AlertUtils;
import com.metro.util.LoggerUtil;
import com.metro.util.RouteUtils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Главный класс JavaFX приложения для расчета путей в метрополитене СПб
 */
public class Main extends Application {
    private static final Logger logger = LoggerUtil.getLogger();
    private Graph graph;
    private DijkstraAlgorithm algorithm;

    private ComboBox<Station> startStationComboBox;
    private ComboBox<Station> endStationComboBox;
    private TextArea routeTextArea;
    private Label timeLabel;
    private Label stationsLabel;
    private Label transfersLabel;
    private ObservableList<Station> allStations;

    @Override
    public void init() throws Exception {
        super.init();
        logger.info("Инициализация приложения MetroApp");
        MetroDataLoader loader = new MetroDataLoader();
        this.graph = loader.loadMetroData();
        this.algorithm = new DijkstraAlgorithm();
        List<Station> sortedStations = graph.getSortedStations();
        allStations = FXCollections.observableArrayList(sortedStations);
    }

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setTop(createHeader());
        root.setCenter(createCenterContent());
        root.setRight(createResultsPanel());
        root.setBottom(createBottomPanel());
        Scene scene = new Scene(root, 1000, 700);

        primaryStage.setTitle("Калькулятор маршрутов метрополитена СПб");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        primaryStage.show();
        logger.info("JavaFX приложение успешно запущено");
    }

    /**
     * Создание верхней панели с заголовком
     */
    private VBox createHeader() {
        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(0, 0, 20, 0));

        Label titleLabel = new Label("Калькулятор маршрутов метрополитена Санкт-Петербурга");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        Label subtitleLabel = new Label("Рассчитайте оптимальный маршрут между станциями");
        subtitleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        subtitleLabel.setTextFill(Color.GRAY);

        headerBox.getChildren().addAll(titleLabel, subtitleLabel);
        return headerBox;
    }

    /**
     * Создание центральной панели с выбором станций
     */
    private VBox createCenterContent() {
        VBox centerBox = new VBox(20);
        centerBox.setPadding(new Insets(20));
        VBox startBox = createStationSelectionBox("Начальная станция:", true);
        VBox endBox = createStationSelectionBox("Конечная станция:", false);
        HBox buttonBox = createButtonPanel();
        centerBox.getChildren().addAll(startBox, endBox, buttonBox);
        return centerBox;
    }

    /**
     * Создание панели выбора станции
     */
    private VBox createStationSelectionBox(String labelText, boolean isStart) {
        VBox box = new VBox(10);

        Label label = new Label(labelText);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        ComboBox<Station> comboBox = createStationComboBox();

        if (isStart) {
            startStationComboBox = comboBox;
        } else {
            endStationComboBox = comboBox;
        }

        box.getChildren().addAll(label, comboBox);
        return box;
    }

    /**
     * Создание ComboBox для выбора станций
     */
    private ComboBox<Station> createStationComboBox() {
        ComboBox<Station> comboBox = new ComboBox<>(allStations);
        comboBox.setPromptText("Выберите станцию...");
        comboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Station station, boolean empty) {
                super.updateItem(station, empty);
                setText(empty || station == null ? null :
                        String.format("%s (%s) [%s]", station.getName(), station.getLine(), station.getId()));
            }
        });

        comboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Station station, boolean empty) {
                super.updateItem(station, empty);
                setText(empty || station == null ? null :
                        String.format("%s (%s)", station.getName(), station.getLine()));
            }
        });

        return comboBox;
    }

    /**
     * Создание панели с кнопками
     */
    private HBox createButtonPanel() {
        Button calculateButton = new Button("Рассчитать маршрут");
        calculateButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        calculateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        calculateButton.setPadding(new Insets(10, 30, 10, 30));
        calculateButton.setOnAction(e -> calculateRoute());

        Button showAllButton = new Button("Показать все станции");
        showAllButton.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        showAllButton.setOnAction(e -> showAllStations());

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(calculateButton, showAllButton);

        return buttonBox;
    }

    /**
     * Создание панели результатов
     */
    private VBox createResultsPanel() {
        VBox rightBox = new VBox(20);
        rightBox.setPadding(new Insets(20));
        rightBox.setMinWidth(300);

        Label resultsLabel = new Label("Результаты расчета");
        resultsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        resultsLabel.setTextFill(Color.DARKGREEN);

        routeTextArea = new TextArea();
        routeTextArea.setEditable(false);
        routeTextArea.setWrapText(true);
        routeTextArea.setFont(Font.font("Monospaced", 12));
        routeTextArea.setPrefHeight(400);
        routeTextArea.setStyle("-fx-background-color: #f5f5f5;");

        rightBox.getChildren().addAll(resultsLabel, routeTextArea, createStatsBox());
        return rightBox;
    }

    /**
     * Создание панели статистики
     */
    private VBox createStatsBox() {
        VBox statsBox = new VBox(10);
        statsBox.setPadding(new Insets(10));
        statsBox.setStyle("-fx-background-color: #e8f5e8; -fx-border-color: #4CAF50; -fx-border-radius: 5;");

        Label statsLabel = new Label("Статистика маршрута");
        statsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        statsLabel.setTextFill(Color.DARKGREEN);

        timeLabel = createStatLabel("Время в пути:", "0 минут");
        stationsLabel = createStatLabel("Станций:", "0");
        transfersLabel = createStatLabel("Пересадок:", "0");

        statsBox.getChildren().addAll(statsLabel, timeLabel, stationsLabel, transfersLabel);
        return statsBox;
    }

    /**
     * Создание метки статистики
     */
    private Label createStatLabel(String title, String defaultValue) {
        HBox box = new HBox(10);
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label valueLabel = new Label(defaultValue);
        valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        valueLabel.setTextFill(Color.DARKRED);
        box.getChildren().addAll(titleLabel, valueLabel);
        Label containerLabel = new Label();
        containerLabel.setGraphic(box);
        return containerLabel;
    }

    /**
     * Создание нижней панели
     */
    private HBox createBottomPanel() {
        HBox bottomBox = new HBox(20);
        bottomBox.setPadding(new Insets(20));
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setStyle("-fx-background-color: #f0f0f0;");

        Label infoLabel = new Label("Всего станций в системе: " + allStations.size());
        infoLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        Button exitButton = new Button("Выход");
        exitButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        exitButton.setOnAction(e -> System.exit(0));

        bottomBox.getChildren().addAll(infoLabel, exitButton);
        return bottomBox;
    }

    /**
     * Расчет маршрута
     */
    private void calculateRoute() {
        Station startStation = startStationComboBox.getValue();
        Station endStation = endStationComboBox.getValue();

        if (startStation == null || endStation == null) {
            AlertUtils.showWarningAlert("Ошибка", "Пожалуйста, выберите начальную и конечную станции");
            return;
        }

        if (startStation.equals(endStation)) {
            AlertUtils.showWarningAlert("Ошибка", "Начальная и конечная станции совпадают");
            return;
        }

        try {
            routeTextArea.setText("Расчет оптимального маршрута...\n");
            PathResult result = algorithm.findShortestPath(graph, startStation, endStation);

            if (result != null) {
                displayRouteResult(result, startStation, endStation);
            } else {
                routeTextArea.setText("Путь между станциями не найден.");
                clearStats();
            }
        } catch (Exception e) {
            routeTextArea.setText("Ошибка при расчете маршрута: " + e.getMessage());
            logger.error("Ошибка при расчете маршрута", e);
            clearStats();
        }
    }

    /**
     * Отображение результатов расчета
     */
    private void displayRouteResult(PathResult result, Station start, Station end) {
        List<Station> path = result.getPath();

        String routeText = String.format(
                "От: %s (%s)\nДо: %s (%s)\n\n%s",
                start.getName(), start.getLine(),
                end.getName(), end.getLine(),
                RouteUtils.getRouteDetails(path, result.getTotalTime())
        );

        routeTextArea.setText(routeText);
        updateStats(result, path);
    }

    /**
     * Обновление статистики маршрута
     */
    private void updateStats(PathResult result, List<Station> path) {
        HBox timeBox = (HBox) timeLabel.getGraphic();
        Label timeValueLabel = (Label) timeBox.getChildren().get(1);
        timeValueLabel.setText(result.getTotalTime() + " минут");
        HBox stationsBox = (HBox) stationsLabel.getGraphic();
        Label stationsValueLabel = (Label) stationsBox.getChildren().get(1);
        stationsValueLabel.setText(String.valueOf(path.size()));
        HBox transfersBox = (HBox) transfersLabel.getGraphic();
        Label transfersValueLabel = (Label) transfersBox.getChildren().get(1);
        transfersValueLabel.setText(String.valueOf(RouteUtils.countTransfers(path)));
    }

    /**
     * Очистка статистики
     */
    private void clearStats() {
        HBox timeBox = (HBox) timeLabel.getGraphic();
        ((Label) timeBox.getChildren().get(1)).setText("0 минут");

        HBox stationsBox = (HBox) stationsLabel.getGraphic();
        ((Label) stationsBox.getChildren().get(1)).setText("0");

        HBox transfersBox = (HBox) transfersLabel.getGraphic();
        ((Label) transfersBox.getChildren().get(1)).setText("0");
    }

    /**
     * Показать все станции в отдельном окне
     */
    private void showAllStations() {
        Stage stationsStage = new Stage();
        stationsStage.setTitle("Список всех станций метрополитена СПб");

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Список всех станций");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        TableView<Station> tableView = new TableView<>();
        tableView.setItems(allStations);

        TableColumn<Station, String> idColumn = new TableColumn<>("Номер");
        idColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));
        idColumn.setPrefWidth(80);

        TableColumn<Station, String> nameColumn = new TableColumn<>("Название станции");
        nameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        nameColumn.setPrefWidth(250);

        TableColumn<Station, String> lineColumn = new TableColumn<>("Линия");
        lineColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLine()));
        lineColumn.setPrefWidth(150);

        tableView.getColumns().addAll(idColumn, nameColumn, lineColumn);

        Label countLabel = new Label("Всего станций: " + allStations.size());
        countLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Button closeButton = new Button("Закрыть");
        closeButton.setOnAction(e -> stationsStage.close());

        root.getChildren().addAll(titleLabel, tableView, countLabel, closeButton);

        Scene scene = new Scene(root, 600, 600);
        stationsStage.setScene(scene);
        stationsStage.show();
    }

    @Override
    public void stop() {
        logger.info("Завершение работы приложения");
    }

    /**
     * Главный метод приложения
     */
    public static void main(String[] args) {
        try {
            System.setProperty("file.encoding", "UTF-8");
            logger.info("Запуск JavaFX приложения");
            launch(args);
        } catch (Exception e) {
            logger.fatal("Критическая ошибка при запуске приложения", e);
            System.err.println("Критическая ошибка: " + e.getMessage());
        }
    }
}