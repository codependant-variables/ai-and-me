package com.codependentvariables.aiandme.controller;


import com.codependentvariables.aiandme.model.CheckIn;
import com.codependentvariables.aiandme.model.QuizAttempt;
import com.codependentvariables.aiandme.model.QuizAttemptSummary;
import com.codependentvariables.aiandme.model.QuizTemplate;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.Toast;
import com.codependentvariables.aiandme.navigation.ToastMessageType;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.services.CheckInService;
import com.codependentvariables.aiandme.services.QuizAttemptService;
import com.codependentvariables.aiandme.services.QuizTemplateService;
import com.codependentvariables.aiandme.state.AppState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import com.codependentvariables.aiandme.services.HomeService;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Controller for the home dashboard view.
 * Displays user statistics, charts, quizzes, and navigation widgets.
 */
public class HomeController {
    @FXML
    private Label checkInStreak;
    @FXML
    private Text lastCheckIn;
    @FXML
    private QuizTemplate quizTemplate;
    @FXML
    private Label checkInName;


    @FXML
    public CategoryAxis xAxisCheckin = new CategoryAxis();
    @FXML
    public NumberAxis yAxisCheckin = new NumberAxis();
    @FXML
    public LineChart<String, Number> checkInChart = new LineChart<String, Number>(xAxisCheckin, yAxisCheckin);

    private CheckInService checkInService = CheckInService.getInstance();
    private QuizAttemptService attemptsService = QuizAttemptService.getInstance();

    @FXML
    public NumberAxis xAxisQuizAttempts = new NumberAxis();
    @FXML
    public NumberAxis yAxisQuizAttempts = new NumberAxis();
    @FXML
    public LineChart<Number, Number> attemptChart = new LineChart<Number, Number>(xAxisQuizAttempts, yAxisQuizAttempts);

    @FXML
    private VBox background;
    @FXML
    private VBox dashboardWidgetData;
    @FXML
    private VBox dashboardWidgetQuiz;
    @FXML
    private VBox dashboardWidgetPuzzle;
    @FXML
    private VBox dashboardWidgetInsight;
    @FXML
    private VBox dashboardWidgetNews;

    // Pie chart comparing quiz and puzzle activity
    public PieChart ratioPie = new PieChart();


    private final HomeService homeService = HomeService.getInstance();
    private final QuizTemplateService quizTemplateService = QuizTemplateService.getInstance();
    private AppState appState = AppState.getInstance();

    /**
     * Initialises dashboard data, charts, and theme bindings.
     */
    public void initialize() {
        checkInStreak.setText(homeService.getCheckInStreak());
        lastCheckIn.setText(homeService.getLastCheckInDate());
        yAxisCheckin.setLowerBound(0.0);
        yAxisCheckin.setUpperBound(100.0);
        yAxisCheckin.setTickUnit(10);

        // Apply dark/light mode styling
        background.styleProperty().bind(appState.getObservableIsDarkMode().map(isDarkMode -> isDarkMode ? "-fx-background-color: #202430;" : "-fx-background-color: #f0edef;"));
        dashboardWidgetData.styleProperty().bind(appState.getObservableIsDarkMode().map(isDarkMode -> isDarkMode ? "-fx-background-color: #2e3440;" : "-fx-background-color: #ffffff;"));
        dashboardWidgetQuiz.styleProperty().bind(appState.getObservableIsDarkMode().map(isDarkMode -> isDarkMode ? "-fx-background-color: linear-gradient(to bottom, #2e3440, #ce78b1);" : "-fx-background-color: linear-gradient(to bottom, #ffffff, #ce78b1);"));
        dashboardWidgetPuzzle.styleProperty().bind(appState.getObservableIsDarkMode().map(isDarkMode -> isDarkMode ? "-fx-background-color: linear-gradient(to bottom, #2e3440, #79d1ed);" : "-fx-background-color: linear-gradient(to bottom, #ffffff, #79d1ed);"));
        dashboardWidgetInsight.styleProperty().bind(appState.getObservableIsDarkMode().map(isDarkMode -> isDarkMode ? "-fx-background-color: #2e3440;" : "-fx-background-color: #ffffff;"));
        dashboardWidgetNews.styleProperty().bind(appState.getObservableIsDarkMode().map(isDarkMode -> isDarkMode ? "-fx-background-color: #2e3440;" : "-fx-background-color: #ffffff;"));

        if (appState.getCurrentUser() != null) {

            XYChart.Series dependenceSeries = new XYChart.Series();
            XYChart.Series useSeries = new XYChart.Series();
            XYChart.Series happinessSeries = new XYChart.Series();

            List<CheckIn> recentCheckins = checkInService.getAllByUserId(appState.getCurrentUser().getId());

            // Add the 5 most recent check-ins to the graph
            for (int i = recentCheckins.size() - 1, count = 0; i >= 0 && count < 5; i--, count++) {
                dependenceSeries.getData().add(new XYChart.Data<>(recentCheckins.get(i).getCompletedAt().toString().substring(0, 10), recentCheckins.get(i).getAiDependence()));
                useSeries.getData().add(new XYChart.Data<>(recentCheckins.get(i).getCompletedAt().toString().substring(0, 10), recentCheckins.get(i).getAiUse()));
                happinessSeries.getData().add(new XYChart.Data<>(recentCheckins.get(i).getCompletedAt().toString().substring(0, 10), recentCheckins.get(i).getAiHappiness()));
            }
            checkInChart.getData().addAll(dependenceSeries, useSeries, happinessSeries);
            dependenceSeries.setName("Dependence");
            useSeries.setName("Frequency");
            happinessSeries.setName("Happiness");

            List<QuizAttempt> recentAttempts = attemptsService.getAttemptsByUser(appState.getCurrentUser().getId());

            XYChart.Series attemptSeries = new XYChart.Series();

            // Add recent quiz attempts to chart
            for (int i = recentAttempts.size() - 1, count = 0; i >= 0 && count < 5; i--, count++) {
                attemptSeries.getData().add(new XYChart.Data<>(recentAttempts.get(i).getCompletedAt().toString().substring(0, 10), recentAttempts.get(i).getId()));
            }

            // Temporary pie chart data
            ObservableList<PieChart.Data> ratioData = FXCollections.observableArrayList(
                    new PieChart.Data("Quizzes", 75),
                    new PieChart.Data("Puzzles", 25));
            ratioPie.setTitle("Attempts by Category");
            ratioPie.setData(ratioData);
        }

        // Load a random recommended quiz
        this.quizTemplate = quizTemplateService.getRandomTemplate();
        checkInName.setText(quizTemplate.getName());
    }

    /**
     * Opens the daily check-in page if the user
     * has not already completed today's check-in.
     */
    public void navigateCheckIn(MouseEvent mouseEvent) {
        if (checkInService.isExistingCheckInToday()) {
            Toast.addMessage("Check-in Completed", "You've already completed your daily check-in. Come back tomorrow!", ToastMessageType.INFORMATION);
        } else {
            Router.navigateLayout(View.CHECK_IN);
        }
    }

    /**
     * Starts the recommended quiz attempt.
     */
    public void handleAttemptQuiz(MouseEvent mouseEvent) {
        QuizAttemptController controller = (QuizAttemptController) Router.navigateLayout(View.QUIZ_ATTEMPT);
        controller.initQuiz(quizTemplate);
    }
}


