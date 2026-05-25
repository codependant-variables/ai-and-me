package com.codependentvariables.aiandme.controller;


import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.modules.Router;
import com.codependentvariables.aiandme.modules.Toast;
import com.codependentvariables.aiandme.modules.ToastMessageType;
import com.codependentvariables.aiandme.modules.View;
import com.codependentvariables.aiandme.services.QuizAttemptService;
import com.codependentvariables.aiandme.services.CheckInService;
import com.codependentvariables.aiandme.services.QuizTemplateService;
import com.codependentvariables.aiandme.services.home.AttemptStatistics;
import com.codependentvariables.aiandme.services.home.CategoryStat;
import com.codependentvariables.aiandme.state.AppState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
    private QuizTemplate puzzleTemplate;
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
    @FXML
    private PieChart ratioPie;


    private final HomeService homeService = HomeService.getInstance();
    private final QuizTemplateService quizTemplateService = QuizTemplateService.getInstance();
    private AppState appState = AppState.getInstance();

    /**
     * Initializes the dashboard view and loads all user analytics data.
     * <p>
     * This includes:
     * <ul>
     *     <li>User check-in streak information</li>
     *     <li>Recent check-in line chart data</li>
     *     <li>Recent quiz attempt scores</li>
     *     <li>Quiz/puzzle category pie chart statistics</li>
     *     <li>Recommended quizzes and puzzles</li>
     * </ul>
     * Applies dynamic styling based on the current theme.
     */
    public void initialize() {

        checkInStreak.setText(homeService.getCheckInStreak());
        lastCheckIn.setText(homeService.getLastCheckInDate());

        yAxisCheckin.setLowerBound(0.0);
        yAxisCheckin.setUpperBound(100.0);
        yAxisCheckin.setTickUnit(10);

        background.styleProperty().bind(
                appState.getObservableIsDarkMode().map(
                        isDarkMode -> isDarkMode
                                ? "-fx-background-color: #202430;"
                                : "-fx-background-color: #f0edef;"
                )
        );

        dashboardWidgetData.styleProperty().bind(
                appState.getObservableIsDarkMode().map(
                        isDarkMode -> isDarkMode
                                ? "-fx-background-color: #2e3440;"
                                : "-fx-background-color: #ffffff;"
                )
        );

        dashboardWidgetQuiz.styleProperty().bind(
                appState.getObservableIsDarkMode().map(
                        isDarkMode -> isDarkMode
                                ? "-fx-background-color: linear-gradient(to bottom, #2e3440, #ce78b1);"
                                : "-fx-background-color: linear-gradient(to bottom, #ffffff, #ce78b1);"
                )
        );

        dashboardWidgetPuzzle.styleProperty().bind(
                appState.getObservableIsDarkMode().map(
                        isDarkMode -> isDarkMode
                                ? "-fx-background-color: linear-gradient(to bottom, #2e3440, #79d1ed);"
                                : "-fx-background-color: linear-gradient(to bottom, #ffffff, #79d1ed);"
                )
        );

        dashboardWidgetInsight.styleProperty().bind(
                appState.getObservableIsDarkMode().map(
                        isDarkMode -> isDarkMode
                                ? "-fx-background-color: #2e3440;"
                                : "-fx-background-color: #ffffff;"
                )
        );

        dashboardWidgetNews.styleProperty().bind(
                appState.getObservableIsDarkMode().map(
                        isDarkMode -> isDarkMode
                                ? "-fx-background-color: #2e3440;"
                                : "-fx-background-color: #ffffff;"
                )
        );

        if (appState.getCurrentUser() != null) {

            User user = appState.getCurrentUser();

            AttemptStatistics split =
                    attemptsService.getAttemptCountByUser(user.getId());

            /////////////////////////////////
            // CHECK-IN GRAPH
            /////////////////////////////////

            List<CheckIn> recentCheckins =
                    checkInService.getAllByUserId(user.getId());

            if (!recentCheckins.isEmpty()) {

                dependenceSeries.setName("AI Dependence");
                useSeries.setName("AI Use");
                happinessSeries.setName("AI Happiness");

                int totalCheckins =
                        Math.min(recentCheckins.size(), 5);

                int checkInNumber = 1;

                for (int i = totalCheckins;
                     i != 0;
                     i--, checkInNumber++) {

                    dependenceSeries.getData().add(
                            new XYChart.Data<>(
                                    checkInNumber,
                                    recentCheckins.get(i - 1).getAiDependence()
                            )
                    );

                    useSeries.getData().add(
                            new XYChart.Data<>(
                                    checkInNumber,
                                    recentCheckins.get(i - 1).getAiUse()
                            )
                    );

                    happinessSeries.getData().add(
                            new XYChart.Data<>(
                                    checkInNumber,
                                    recentCheckins.get(i - 1).getAiHappiness()
                            )
                    );
                }

                checkInChart.getData().addAll(
                        dependenceSeries,
                        useSeries,
                        happinessSeries
                );
            }

            /////////////////////////////////
            // QUIZ ATTEMPT GRAPH
            /////////////////////////////////

            List<QuizAttempt> recentAttempts =
                    attemptsService.getAttemptsByUser(user.getId());

            XYChart.Series<Number, Number> attemptSeries =
                    new XYChart.Series<>();

            attemptSeries.setName("Score (out of 10)");

            int startIndex =
                    Math.max(0, recentAttempts.size() - 5);

            int attemptNumber = 1;

            for (int i = startIndex;
                 i < recentAttempts.size();
                 i++, attemptNumber++) {

                attemptSeries.getData().add(
                        new XYChart.Data<>(
                                attemptNumber,
                                recentAttempts.get(i).getResults()
                        )
                );
            }

            attemptChart.getData().add(attemptSeries);

            /////////////////////////////////
            // PIE CHART
            /////////////////////////////////

            ObservableList<PieChart.Data> ratioData =
                    FXCollections.observableArrayList();

            List<CategoryStat> stats =
                    split.getCategoryStats();
            if (stats != null) {
                for (CategoryStat stat : stats) {
                    String type = stat.isPuzzle() ? "Puzzle" : "Quiz";
                    String categoryName = stat.getCategoryName();

                    if (categoryName == null) {
                        categoryName = "General";
                    }
                    ratioData.add(new PieChart.Data(type + " - " + stat.getCategoryName(), stat.getCount()));
                }
            }

            ratioPie.setTitle("Attempts by Category");
            ratioPie.setData(ratioData);

            System.out.println("Pie slices: " + ratioData.size()
            );

            for (PieChart.Data data : ratioData) {
                System.out.println(data.getName() + " = " + data.getPieValue());
            }

            /////////////////////////////////
            // RECOMMENDED QUIZZES
            /////////////////////////////////

            this.quizTemplate = quizTemplateService.getRandomTemplate();

            this.puzzleTemplate = quizTemplateService.getRandomPuzzle();

            System.out.println("Quiz: " + quizTemplate);
            System.out.println("Puzzle: " + puzzleTemplate);

            if (quizTemplate != null) {
                checkInName.setText(quizTemplate.getName());
            }
        }
    }

    /**
     * Navigates the user to the daily check-in page.
     * Guest users can always access the check-in page.
     * Logged-in users are prevented from completing multiple
     * check-ins on the same day.
     *
     * @param mouseEvent mouse click event triggered by the user
     */
    public void navigateCheckIn(MouseEvent mouseEvent) {
        // Guest users can always access check-ins
        if (appState.getCurrentUser() == null) {

            Router.navigateLayout(View.CHECK_IN);
            return;
        }
        // Logged-in user check
        if (checkInService.isExistingCheckInToday()) {
            Toast.addMessage(
                    "Check-in Completed",
                    "You've already completed your daily check-in. Come back tomorrow!",
                    ToastMessageType.INFORMATION
            );
        } else {
            Router.navigateLayout(View.CHECK_IN);
        }
    }

    /**
     * Opens the recommended quiz attempt page
     * and loads the selected quiz template.
     *
     * @param mouseEvent mouse click event triggered by the user
     */
    public void handleAttemptQuiz(MouseEvent mouseEvent) {
        QuizAttemptController controller = (QuizAttemptController) Router.navigateLayout(View.QUIZ_ATTEMPT);
        controller.initQuiz(quizTemplate);
    }

    /**
     * Opens the recommended puzzle attempt page
     * and loads the selected puzzle template.
     *
     * @param mouseEvent mouse click event triggered by the user
     */
    public void handleAttemptPuzzle(MouseEvent mouseEvent) {
        QuizAttemptController controller = (QuizAttemptController) Router.navigateLayout(View.QUIZ_ATTEMPT);
        controller.initQuiz(puzzleTemplate);
    }}


