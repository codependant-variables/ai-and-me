package com.codependentvariables.aiandme.controller;


import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.modules.Router;
import com.codependentvariables.aiandme.modules.Toast;
import com.codependentvariables.aiandme.modules.ToastMessageType;
import com.codependentvariables.aiandme.modules.View;
import com.codependentvariables.aiandme.services.QuizAttemptService;
import com.codependentvariables.aiandme.services.CheckInService;
import com.codependentvariables.aiandme.services.QuizTemplateService;
import com.codependentvariables.aiandme.state.AppState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import com.codependentvariables.aiandme.services.HomeService;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

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
    public NumberAxis xAxisCheckin;
    @FXML
    public NumberAxis yAxisCheckin;
    @FXML
    public XYChart<Number,Number> checkInChart;
    @FXML
    public XYChart.Series<Number, Number> dependenceSeries = new XYChart.Series<Number, Number>();
    @FXML
    public XYChart.Series<Number, Number> useSeries = new XYChart.Series<Number, Number>();
    @FXML
    public XYChart.Series<Number, Number> happinessSeries = new XYChart.Series<Number, Number>();

    private CheckInService checkInService = CheckInService.getInstance();
    private QuizAttemptService attemptsService = QuizAttemptService.getInstance();

    @FXML
    public NumberAxis xAxisQuizAttempts;
    @FXML
    public NumberAxis yAxisQuizAttempts;
    @FXML
    public XYChart<Number, Number> attemptChart; //= new LineChart<Number, Number>(xAxisQuizAttempts, yAxisQuizAttempts);
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

    //  quiz vs puzzle pie chart idk
    public PieChart ratioPie = new PieChart();


    private final HomeService homeService = HomeService.getInstance();
    private final QuizTemplateService quizTemplateService = QuizTemplateService.getInstance();
    private AppState appState = AppState.getInstance();

    public void initialize() {
        checkInStreak.setText(homeService.getCheckInStreak());
        lastCheckIn.setText(homeService.getLastCheckInDate());
        yAxisCheckin.setLowerBound(0.0);
        yAxisCheckin.setUpperBound(100.0);
        yAxisCheckin.setTickUnit(10);
        background.styleProperty().bind(appState.getObservableIsDarkMode().map(isDarkMode -> isDarkMode ? "-fx-background-color: #202430;" : "-fx-background-color: #f0edef;"));
        dashboardWidgetData.styleProperty().bind(appState.getObservableIsDarkMode().map(isDarkMode -> isDarkMode ? "-fx-background-color: #2e3440;" : "-fx-background-color: #ffffff;"));
        dashboardWidgetQuiz.styleProperty().bind(appState.getObservableIsDarkMode().map(isDarkMode -> isDarkMode ? "-fx-background-color: linear-gradient(to bottom, #2e3440, #ce78b1);" : "-fx-background-color: linear-gradient(to bottom, #ffffff, #ce78b1);"));
        dashboardWidgetPuzzle.styleProperty().bind(appState.getObservableIsDarkMode().map(isDarkMode -> isDarkMode ? "-fx-background-color: linear-gradient(to bottom, #2e3440, #79d1ed);" : "-fx-background-color: linear-gradient(to bottom, #ffffff, #79d1ed);"));
        dashboardWidgetInsight.styleProperty().bind(appState.getObservableIsDarkMode().map(isDarkMode -> isDarkMode ? "-fx-background-color: #2e3440;" : "-fx-background-color: #ffffff;"));
        dashboardWidgetNews.styleProperty().bind(appState.getObservableIsDarkMode().map(isDarkMode -> isDarkMode ? "-fx-background-color: #2e3440;" : "-fx-background-color: #ffffff;"));
        checkInChart.setLegendVisible(true);
        checkInChart.setLegendSide(Side.RIGHT);
        dependenceSeries.setName("Dependence");
        useSeries.setName("Frequency");
        happinessSeries.setName("Happiness");


        if (appState.getCurrentUser() != null) {
            AttemptStatistics split = attemptsService.getAttemptCountByUser(appState.getCurrentUser().getId());

            User user = appState.getCurrentUser();
            if (user != null) {
                List<CheckIn> recentCheckins = checkInService.getAllByUserId(appState.getCurrentUser().getId());

                if (!recentCheckins.isEmpty()) {
                    dependenceSeries.setName("Dependence");
                    useSeries.setName("Frequency");
                    happinessSeries.setName("Happiness");
                    int totalCheckins = Math.min(recentCheckins.size(), 5);
                    int checkInNumber = 1;
                    for (int i = totalCheckins; i != 0; i--, checkInNumber++) {
                        //for every check in, create a point on a line graph for each category (requires time/date string and score float)
                        dependenceSeries.getData().add(new XYChart.Data<>(checkInNumber, recentCheckins.get(i - 1).getAiDependence()));
                        useSeries.getData().add(new XYChart.Data<>(checkInNumber, recentCheckins.get(i - 1).getAiUse()));
                        happinessSeries.getData().add(new XYChart.Data<>(checkInNumber, recentCheckins.get(i - 1).getAiHappiness()));
                    }

                    xAxisCheckin.setAutoRanging(false);
                    xAxisCheckin.setLowerBound(1);
                    xAxisCheckin.setUpperBound(5);
                    xAxisCheckin.setTickUnit(1);
                    yAxisCheckin.setAutoRanging(false);
                    yAxisCheckin.setLowerBound(0);
                    yAxisCheckin.setUpperBound(10.0);
                    yAxisCheckin.setTickUnit(10);

                    xAxisQuizAttempts.setTickUnit(1);
                    yAxisQuizAttempts.setUpperBound(100.0);

                    dependenceSeries.setName("Dependence");
                    useSeries.setName("Frequency");
                    happinessSeries.setName("Happiness");
                    checkInChart.setLegendVisible(true);
                    checkInChart.setLegendSide(Side.RIGHT);
                    checkInChart.getData().addAll(dependenceSeries, useSeries, happinessSeries);
                    ///////////////////////////
                    List<QuizAttempt> recentAttempts = attemptsService.getAttemptsByUser(appState.getCurrentUser().getId());

                    XYChart.Series<Number, Number> attemptSeries = new XYChart.Series<>();
                    attemptSeries.setName("Score (out of 10)");

                    int startIndex = Math.max(0, recentAttempts.size() - 5);
                    int attemptNumber = 1;
                    for (int i = startIndex; i < recentAttempts.size(); i++, attemptNumber++) {
                        attemptSeries.getData().add(new XYChart.Data<>(attemptNumber, recentAttempts.get(i).getResults()));
                    }

                    int totalPlotted = recentAttempts.size() - startIndex;
                    xAxisQuizAttempts.setAutoRanging(false);
                    xAxisQuizAttempts.setLowerBound(1);
                    xAxisQuizAttempts.setUpperBound(Math.max(totalPlotted, 1));
                    xAxisQuizAttempts.setTickUnit(1);
                    yAxisQuizAttempts.setAutoRanging(false);
                    yAxisQuizAttempts.setLowerBound(0);
                    yAxisQuizAttempts.setUpperBound(10);
                    yAxisQuizAttempts.setTickUnit(1);

                    attemptChart.getData().add(attemptSeries);
                    ratioPie.setTitle("Score");

                    ///////////////////////////

                    //  dummy data
                    ObservableList<PieChart.Data> ratioData = FXCollections.observableArrayList(
                            new PieChart.Data("Quizzes", split.getQuizCount()),
                            new PieChart.Data("Puzzles", split.getPuzzleCount()));
                    ratioPie.setTitle("Attempts by Category");
                    ratioPie.setData(ratioData);
                }
                this.quizTemplate = quizTemplateService.getRandomTemplate();
                checkInName.setText(quizTemplate.getName());
            }
        }
    }

    public void navigateCheckIn(MouseEvent mouseEvent) {
        // TODO: fix existing checkin today
        if (checkInService.isExistingCheckInToday()) {
            Toast.addMessage("Check-in Completed", "You've already completed your daily check-in. Come back tomorrow!", ToastMessageType.INFORMATION);
        } else {
            Router.navigateLayout(View.CHECK_IN);
        }
    }

    public void handleAttemptQuiz(MouseEvent mouseEvent) {
        QuizAttemptController controller = (QuizAttemptController) Router.navigateLayout(View.QUIZ_ATTEMPT);
        controller.initQuiz(quizTemplate);
    }
}


