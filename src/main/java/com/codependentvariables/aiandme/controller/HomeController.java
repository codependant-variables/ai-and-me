package com.codependentvariables.aiandme.controller;


import com.codependentvariables.aiandme.model.CheckIn;
import com.codependentvariables.aiandme.model.QuizTemplate;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.services.CheckInService;
import com.codependentvariables.aiandme.services.QuizTemplateService;
import com.codependentvariables.aiandme.state.AppState;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
    public CategoryAxis xAxis = new CategoryAxis();
    @FXML
    public NumberAxis yAxis = new NumberAxis();
    @FXML
    public LineChart<String,Number> checkInChart = new LineChart<String,Number>(xAxis,yAxis);

    private CheckInService checkInService = CheckInService.getInstance();


    private final HomeService homeService = HomeService.getInstance();
    private final QuizTemplateService quizTemplateService = QuizTemplateService.getInstance();
    private AppState appState = AppState.getInstance();




    public void initialize() {
        checkInStreak.setText(homeService.getCheckInStreak());
        lastCheckIn.setText(homeService.getLastCheckInDate());
        yAxis.setLowerBound(0.0);
        yAxis.setUpperBound(100.0);
        yAxis.setTickUnit(10);


        if (appState.getCurrentUser() != null) {

            XYChart.Series dependenceSeries = new XYChart.Series();
            dependenceSeries.setName("Dependence");
            XYChart.Series useSeries = new XYChart.Series();
            useSeries.setName("Frequency");

            XYChart.Series happinessSeries = new XYChart.Series();
            happinessSeries.setName("Happiness");
            //happinessSeries.getName();


            List<CheckIn> recentCheckins = checkInService.getAllByUserId(appState.getCurrentUser().getId());
            // using getAllByUserId for now - may want to create a separate method for getRecentCheckIns in CheckInService class which checks timeframe

            List<CheckIn> dailyCheckins = checkInService.getAllByUserId(appState.getCurrentUser().getId());


            //for (CheckIn checkIn : recentCheckins; ) {
            //^^ Unused foreach loop


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
            /*
            /////////////////////
            XYChart.Series dependenceSeries = new XYChart.Series();
            dependenceSeries.setName("Dependence");
            dependenceSeries.getData().add(new XYChart.Data<>("date1", 5));
            dependenceSeries.getData().add(new XYChart.Data<>("date2", 7.3));
            /////////////////////
            XYChart.Series useSeries = new XYChart.Series();
            useSeries.setName("Use");
            useSeries.getData().add(new XYChart.Data<>("date1", 4));
            useSeries.getData().add(new XYChart.Data<>("date2", 7.99));
            /////////////////////
            XYChart.Series happinessSeries = new XYChart.Series();
            happinessSeries.setName("Happiness");
            happinessSeries.getData().add(new XYChart.Data<>("date1", 6));
            happinessSeries.getData().add(new XYChart.Data<>("date2", 3.99));
            /////////////////////
            *\*/
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


