package com.codependentvariables.aiandme.controller;


import com.codependentvariables.aiandme.model.CheckIn;
import com.codependentvariables.aiandme.model.QuizAttempt;
import com.codependentvariables.aiandme.model.QuizAttemptSummary;
import com.codependentvariables.aiandme.model.QuizTemplate;
import com.codependentvariables.aiandme.navigation.Router;
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
    public CategoryAxis xAxisCheckin = new CategoryAxis();
    @FXML
    public NumberAxis yAxisCheckin = new NumberAxis();
    @FXML
    public LineChart<String,Number> checkInChart = new LineChart<String,Number>(xAxisCheckin,yAxisCheckin);

    private CheckInService checkInService = CheckInService.getInstance();
    private QuizAttemptService attemptsService = QuizAttemptService.getInstance();

    @FXML
    public NumberAxis xAxisQuizAttempts = new NumberAxis();
    @FXML
    public NumberAxis yAxisQuizAttempts = new NumberAxis();
    @FXML
    public LineChart<Number,Number> attemptChart = new LineChart<Number,Number>(xAxisQuizAttempts,yAxisQuizAttempts);

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

        if (appState.getCurrentUser() != null) {

            XYChart.Series dependenceSeries = new XYChart.Series();
            XYChart.Series useSeries = new XYChart.Series();
            XYChart.Series happinessSeries = new XYChart.Series();

            List<CheckIn> recentCheckins = checkInService.getAllByUserId(appState.getCurrentUser().getId());
            // using getAllByUserId for now - may want to create a separate method for getRecentCheckIns in CheckInService class which checks timeframe

            // for loop to go backwards five times maximum
            for (int i = recentCheckins.size() - 1, count = 0; i >= 0 && count < 5; i--, count++) {
                //while (recentCheckins.get(i).getCompletedAt() !=)
                //for every check in, create a point on a line graph for each category (requires time/date string and score float)
                System.out.println("Completed at: ");
                System.out.println(recentCheckins.get(i).getCompletedAt().toString());
                System.out.println("Dependence: ");
                System.out.println(recentCheckins.get(i).getAiDependence());
                System.out.println("Happiness: ");
                System.out.println(recentCheckins.get(i).getAiHappiness());
                System.out.println("Use (frequency): ");
                System.out.println(recentCheckins.get(i).getAiUse());
                dependenceSeries.getData().add(new XYChart.Data<>(recentCheckins.get(i).getCompletedAt().toString().substring(0,10) ,recentCheckins.get(i).getAiDependence()));
                useSeries.getData().add(new XYChart.Data<>(recentCheckins.get(i).getCompletedAt().toString().substring(0,10) ,recentCheckins.get(i).getAiUse()));
                happinessSeries.getData().add(new XYChart.Data<>(recentCheckins.get(i).getCompletedAt().toString().substring(0,10),recentCheckins.get(i).getAiHappiness()));
            }
            checkInChart.getData().addAll(dependenceSeries, useSeries, happinessSeries);
            dependenceSeries.setName("Dependence");
            useSeries.setName("Frequency");
            happinessSeries.setName("Happiness");
            ///////////////////////////

            List<QuizAttempt> recentAttempts = attemptsService.getAttemptsByUser(appState.getCurrentUser().getId());

            XYChart.Series attemptSeries = new XYChart.Series();

            for (int i = recentAttempts.size() - 1, count = 0; i >= 0 && count < 5; i--, count++) {

                //probably need to use the quizattemptsummary object with correlated id to get percentage correct or something like that

                attemptSeries.getData().add(new XYChart.Data<>(recentAttempts.get(i).getCompletedAt().toString().substring(0,10) ,recentAttempts.get(i).getId()));
            }
            ///////////////////////////

            //  dummy data
            ObservableList<PieChart.Data> ratioData =  FXCollections.observableArrayList(
                    new PieChart.Data("Quizzes", 75),
                    new PieChart.Data("Puzzles", 25));
            ratioPie.setTitle("Attempts by Category");
            ratioPie.setData(ratioData);
        }
        this.quizTemplate = quizTemplateService.getRandomTemplate();
        checkInName.setText(quizTemplate.getName());
    }

    public void navigateCheckIn(MouseEvent mouseEvent) {
        Router.navigateLayout(View.CHECK_IN);
    }

    public void handleAttemptQuiz(MouseEvent mouseEvent) {
        QuizAttemptController controller = (QuizAttemptController)Router.navigateLayout(View.QUIZ_ATTEMPT);
        controller.initQuiz(quizTemplate);
    }
}


