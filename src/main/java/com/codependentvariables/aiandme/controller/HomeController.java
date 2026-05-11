package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.CheckIn;
import com.codependentvariables.aiandme.model.QuizTemplate;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.services.CheckInService;
import com.codependentvariables.aiandme.services.QuizTemplateService;
import com.codependentvariables.aiandme.state.AppState;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
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
    public LineChart checkInChart;


    private final HomeService homeService = HomeService.getInstance();
    private final QuizTemplateService quizTemplateService = QuizTemplateService.getInstance();

    private CheckInService checkInService = CheckInService.getInstance();
    private AppState appState = AppState.getInstance();

    public void initialize() {
        checkInStreak.setText(homeService.getCheckInStreak());
        lastCheckIn.setText(homeService.getLastCheckInDate());

        if (appState.getCurrentUser() != null) {
            List<CheckIn> recentCheckins = checkInService.getAllByUserId(appState.getCurrentUser().getId());
            // using getAllByUserId for now - may want to create a separate method for getRecentCheckIns in CheckInService class which checks timeframe

            XYChart.Series dependenceSeries = new XYChart.Series();
            dependenceSeries.setName("Dependence");

            XYChart.Series useSeries = new XYChart.Series();
            dependenceSeries.setName("Use");

            XYChart.Series happinessSeries = new XYChart.Series();
            dependenceSeries.setName("Happiness");

            for (CheckIn checkIn : recentCheckins) {
                dependenceSeries.getData().add(new XYChart.Data(checkIn.getCompletedAt(), checkIn.getAiDependence()));
                useSeries.getData().add(new XYChart.Data(checkIn.getCompletedAt(), checkIn.getAiUse()));
                happinessSeries.getData().add(new XYChart.Data(checkIn.getCompletedAt(), checkIn.getAiHappiness()));
            }git

            checkInChart.getData().addAll(dependenceSeries, useSeries, happinessSeries);
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


