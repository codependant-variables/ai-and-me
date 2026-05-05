package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.services.QuizAttemptService;
import com.codependentvariables.aiandme.services.QuizTemplateService;
import com.codependentvariables.aiandme.state.AppState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class QuizAttemptController {

    private QuizTemplate template;
    private List<QuizTemplateQuestion> questions = new ArrayList<>();
    private int currentIndex = 0;

    private final List<QuizTemplateAnswer> selectedAnswers = new ArrayList<>();

    @FXML private Label progressLabel;
    @FXML private Label questionLabel;
    @FXML private VBox answersBox;
    @FXML private Button nextButton;

    @FXML
    private void initialize() {
        progressLabel.setText("No quiz loaded.");
        questionLabel.setText("");
        answersBox.getChildren().clear();
        nextButton.setDisable(true);
    }

    public void initQuiz(QuizTemplate template) {
        if (template == null) {
            showWarning("No quiz template was selected.");
            navigateBack();
            return;
        }

        this.template = template;

        QuizTemplateService.getInstance().loadQuestionsIntoTemplate(template);
        this.questions = template.getQuestions();

        System.out.println("Quiz loaded: " + template.getName());
        System.out.println("Question count: " + (questions == null ? 0 : questions.size()));

        this.currentIndex = 0;
        this.selectedAnswers.clear();

        if (questions == null || questions.size() < 2) {
            showWarning("This quiz needs at least 2 questions before it can be attempted.");
            navigateBack();
            return;
        }

        loadQuestion();
    }

    private void loadQuestion() {
        QuizTemplateQuestion question = questions.get(currentIndex);

        progressLabel.setText("Question " + (currentIndex + 1) + " of " + questions.size());
        questionLabel.setText(question.getText());
        answersBox.getChildren().clear();

        List<QuizTemplateAnswer> answers = question.getAnswers();

        System.out.println("Question: " + question.getText());
        System.out.println("Answer count: " + (answers == null ? 0 : answers.size()));

        if (answers == null || answers.isEmpty()) {
            answersBox.getChildren().add(new Label("No answers found for this question."));
            nextButton.setDisable(true);
            return;
        }

        ToggleGroup group = new ToggleGroup();

        for (QuizTemplateAnswer answer : answers) {
            RadioButton radioButton = new RadioButton(answer.getText());
            radioButton.setToggleGroup(group);
            radioButton.setUserData(answer);
            radioButton.setWrapText(true);
            answersBox.getChildren().add(radioButton);
        }

        nextButton.setDisable(true);

        group.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            nextButton.setDisable(newToggle == null);
        });

        nextButton.setText(currentIndex == questions.size() - 1 ? "Finish" : "Next >");
    }

    @FXML
    private void handleNext() {
        QuizTemplateAnswer selectedAnswer = getSelectedAnswer();

        if (selectedAnswer == null) return;

        selectedAnswers.add(selectedAnswer);

        if (currentIndex < questions.size() - 1) {
            currentIndex++;
            loadQuestion();
        } else {
            finishQuiz();
        }
    }

    private QuizTemplateAnswer getSelectedAnswer() {
        for (javafx.scene.Node node : answersBox.getChildren()) {
            if (node instanceof RadioButton rb && rb.isSelected()) {
                return (QuizTemplateAnswer) rb.getUserData();
            }
        }

        return null;
    }

    private void finishQuiz() {
        User currentUser = AppState.getInstance().getCurrentUser();
        int userId = currentUser != null ? currentUser.getId() : 0;

        try {
            int correct = QuizAttemptService.getInstance()
                    .saveAttempt(template, userId, selectedAnswers);

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/codependentvariables/aiandme/quiz-attempt-results.fxml")
            );

            Parent view = loader.load();

            QuizAttemptResultsController controller = loader.getController();

            controller.initResults(
                    QuizAttemptSummary.of(
                            template.getName(),
                            correct,
                            questions.size(),
                            java.sql.Timestamp.from(java.time.Instant.now()),
                            questions,
                            selectedAnswers
                    )
            );

            Router.setLayoutContent(view, View.QUIZ_ATTEMPT_RESULTS);

        } catch (Exception e) {
            e.printStackTrace();
            showWarning("Failed to save quiz attempt: " + e.getMessage());
        }
    }

    @FXML
    private void navigateBack() {
        Router.navigateLayout(View.QUIZ_LIBRARY);
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Quiz Attempt");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}