package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.QuizTemplate;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.services.QuizTemplateService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.codependentvariables.aiandme.services.HomeService;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class HomeController {
    @FXML
    private Label checkInStreak;
    @FXML
    private Text lastCheckIn;
    @FXML
    private QuizTemplate quizTemplate;
    @FXML
    private Label checkInName;

    private final HomeService homeService = HomeService.getInstance();
    private final QuizTemplateService quizTemplateService = QuizTemplateService.getInstance();

    public void initialize() {
        checkInStreak.setText(homeService.getCheckInStreak());
        lastCheckIn.setText(homeService.getLastCheckInDate());

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


