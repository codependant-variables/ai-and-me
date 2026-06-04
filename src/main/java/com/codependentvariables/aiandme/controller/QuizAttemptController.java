package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.modules.dialogue.Dialogue;
import com.codependentvariables.aiandme.modules.router.Router;
import com.codependentvariables.aiandme.modules.router.View;
import com.codependentvariables.aiandme.services.QuizAttemptService;
import com.codependentvariables.aiandme.services.QuizTemplateService;
import com.codependentvariables.aiandme.modules.state.AppState;
import javafx.fxml.FXML;
import java.util.logging.Logger;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for quiz attempts.
 * Handles quiz loading, question navigation,
 * answer selection, and quiz submission.
 */
public class QuizAttemptController {
    private static final AppState appState = AppState.getInstance();
    private static final QuizAttemptService quizAttemptService = QuizAttemptService.getInstance();
    private static final Logger logger = Logger.getLogger(QuizAttemptController.class.getName()); // Logger used for quiz attempt debugging.

    private QuizTemplate template;
    private List<QuizTemplateQuestion> questions = new ArrayList<>();
    private int currentIndex = 0;

    // Stores answers selected by the user
    public final List<QuizTemplateAnswer> selectedAnswers = new ArrayList<>();

    private boolean testMode = false;
    private int totalQuestions;

    @FXML
    public Label progressLabel;
    @FXML
    public Label questionLabel;
    @FXML
    public ImageView questionImageView;
    @FXML
    public VBox answersBox;
    @FXML
    public Button nextButton;

    /**
     * Enables or disables test mode.
     * In test mode, UI actions and external calls are skipped.
     * @param testMode testMode true to enable test mode, false otherwise
     */
    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    /**
     * Initialises the quiz attempt UI.
     */
    @FXML
    private void initialize() {
        progressLabel.setText("No quiz loaded.");
        questionLabel.setText("");
        answersBox.getChildren().clear();
        nextButton.setDisable(true);
    }

    /**
     * Loads and validates a quiz template.
     *
     * @param template quiz template to attempt
     */
    public void initQuiz(QuizTemplate template) {
        if (template == null) {
            Dialogue.message("No quiz template was selected.");
            navigateBack();
            return;
        }

        this.template = template;

        if (!testMode) {
            QuizTemplateService.getInstance().loadQuestionsIntoTemplate(template);
        }
        this.questions = template.getQuestions();

        // Validate question answer lists before user starts quiz
        for (QuizTemplateQuestion question : questions) {
            if (question.getAnswers() == null || question.getAnswers().isEmpty()) {
                Dialogue.message("Oops, this quiz is broken.");
                navigateBack();
                return;
            }
        }

        logger.info("Quiz loaded: " + template.getName());
        logger.info("Question count: " + (questions == null ? 0 : questions.size()));

        this.totalQuestions = questions.size();
        this.currentIndex = 0;
        this.selectedAnswers.clear();

        loadQuestion();
        updateProgress();
    }

    /**
     * Updates the progress label to show the current question number
     */
    private void updateProgress() {
        progressLabel.setText("Question " + (currentIndex + 1) + " of " + totalQuestions);
    }

    /**
     * Returns the current question number.
     * Used mainly for unit testing.
     *
     * @return current question number
     */
    public int getQuestionNumber() {
        return currentIndex + 1;
    }

    /**
     * Loads the current quiz question and answers.
     */
    private void loadQuestion() {
        QuizTemplateQuestion question = questions.get(currentIndex);

        questionLabel.setText(question.getText());
        answersBox.getChildren().clear();

        // Show image for puzzle attempts
        if (template.isPuzzle() && questionImageView != null) {
            byte[] imgBytes = question.getImage();
            if (imgBytes != null && imgBytes.length > 0) {
                questionImageView.setImage(new Image(new ByteArrayInputStream(imgBytes)));
                questionImageView.setVisible(true);
                questionImageView.setManaged(true);
            } else {
                questionImageView.setImage(null);
                questionImageView.setVisible(false);
                questionImageView.setManaged(false);
            }
        }

        List<QuizTemplateAnswer> answers = question.getAnswers();

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

        group.selectedToggleProperty().addListener((obs, oldToggle, newToggle) ->
                nextButton.setDisable(newToggle == null)
        );

        boolean isLastQuestion = currentIndex == totalQuestions - 1;
        nextButton.setText(isLastQuestion ? "Finish" : "Next >");
    }

    /**
     * Handles moving to the next question or finishing the quiz.
     */
    @FXML
    public void handleNext() {
        QuizTemplateAnswer selectedAnswer = getSelectedAnswer();
        if (selectedAnswer == null) return;

        selectedAnswers.add(selectedAnswer);

        if (currentIndex < questions.size() - 1) {
            currentIndex++;
            loadQuestion();
            updateProgress();
        } else {
            submitQuiz();
        }
    }

    /**
     * Returns the currently selected answer.
     *
     * @return selected answer or null
     */
    private QuizTemplateAnswer getSelectedAnswer() {
        for (javafx.scene.Node node : answersBox.getChildren()) {
            if (node instanceof RadioButton rb && rb.isSelected()) {
                return (QuizTemplateAnswer) rb.getUserData();
            }
        }

        return null;
    }

    /**
     * Submits the quiz attempt.
     * Guest users are prompted to sign up first.
     */
    private void submitQuiz() {
        if (testMode) return;
        if (appState.getCurrentUser() == null) {
            Dialogue.signupToSave(this::finishSubmitQuiz, this::finishSubmitQuiz);
        } else {
            this.finishSubmitQuiz();
        }
    }

    /**
     * Finalizes quiz submission and opens the results page.
     */
    private void finishSubmitQuiz() {
        int correct = appState.getCurrentUser() == null
                ? (int)selectedAnswers.stream().filter(QuizTemplateAnswer::isCorrect).count()
                : quizAttemptService.saveAttempt(template, appState.getCurrentUser().getId(), selectedAnswers);

        QuizAttemptSummary summary = QuizAttemptSummary.of(
                template.getName(),
                correct,
                questions.size(),
                java.sql.Timestamp.from(java.time.Instant.now()),
                questions,
                selectedAnswers
        );

        QuizAttemptResultsController controller = (QuizAttemptResultsController) Router.navigateLayout(View.QUIZ_ATTEMPT_RESULTS);
        assert controller != null;
        controller.initialiseData(summary);
    }

    @FXML
    private void navigateBack() {
        Dialogue.confirmationWithCancel(result -> {
            if (result != null && result) {
                Router.navigateBack();
            }
        });
    }
}