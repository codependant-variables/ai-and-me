package com.codependentvariables.aiandme.controllers;

import com.codependentvariables.aiandme.JavaFXTest;
import com.codependentvariables.aiandme.controller.QuizAttemptController;
import com.codependentvariables.aiandme.model.QuizTemplate;
import com.codependentvariables.aiandme.model.QuizTemplateAnswer;
import com.codependentvariables.aiandme.model.QuizTemplateQuestion;
import com.codependentvariables.aiandme.modules.dialogue.Dialogue;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* Research
* https://docs.junit.org/5.0.0/api/org/junit/jupiter/api/BeforeAll.html
* https://docs.oracle.com/en/java/java-components/javafx/21/docs/javafx.graphics/javafx/application/Platform.html
* https://stackoverflow.com/questions/11385604/how-do-you-unit-test-a-javafx-controller-with-junit
* https://www.javamex.com/tutorials/threads/CountDownLatch.shtml
* https://www.baeldung.com/java-arrays-aslist-vs-list-of
* https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ToggleGroup.html
* https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/RadioButton.html
 */
public class QuizAttemptControllerTest extends JavaFXTest {
    private QuizAttemptController quizAttemptController;
    private final ToggleGroup answerGroup = new ToggleGroup();

    @BeforeEach
    void setUp() {
        quizAttemptController = new QuizAttemptController();

        quizAttemptController.setTestMode(true);

        // Adds UI dependencies
        quizAttemptController.questionLabel = new Label();
        quizAttemptController.progressLabel = new Label();
        quizAttemptController.answersBox = new VBox();
        quizAttemptController.nextButton = new Button();
    }

    private QuizTemplate buildTemplate() {
        QuizTemplate quizTemplate = new QuizTemplate("Sample Quiz", 1, 1, "open");

        QuizTemplateQuestion q1 = new QuizTemplateQuestion(1, "q1");
        q1.setAnswers(List.of(
                new QuizTemplateAnswer(1, "A1", true),
                new QuizTemplateAnswer(1, "A2", false)
        ));

        QuizTemplateQuestion q2 = new QuizTemplateQuestion(1, "q2");
        q2.setAnswers(List.of(
                new QuizTemplateAnswer(1, "B1", true),
                new QuizTemplateAnswer(1, "B2", false)
        ));

        quizTemplate.setQuestions(List.of(q1, q2));
        return quizTemplate;
    }

    @Test
    void quizStarts() {
        // Checks quiz starts with the first question loaded
        quizAttemptController.initQuiz(buildTemplate());

        assertEquals(1, quizAttemptController.getQuestionNumber());
        assertEquals("Question 1 of 2", quizAttemptController.progressLabel.getText());
    }

    @Test
    void checkAdvanceNoAnswer() {
        // Quiz cannot advance when an answer is not selected
        quizAttemptController.initQuiz(buildTemplate());

        quizAttemptController.handleNext(); // no selection

        assertEquals(1, quizAttemptController.getQuestionNumber());
    }

    @Test
    void checkAdvanceAnswer() {
        // Quiz advances when answer is selected
        quizAttemptController.initQuiz(buildTemplate());

        selectAnswer("A1");
        quizAttemptController.handleNext();

        assertEquals(2, quizAttemptController.getQuestionNumber());
    }

    @Test
    void quizProgressesCorrectly() {
        quizAttemptController.initQuiz(buildTemplate());

        assertEquals("Question 1 of 2", quizAttemptController.progressLabel.getText());

        selectAnswer("A1");
        quizAttemptController.handleNext();

        assertEquals("Question 2 of 2", quizAttemptController.progressLabel.getText());
    }

    @Test
    void answersReset() {
        // Checks that the answers will reset on the next quesiton
        quizAttemptController.initQuiz(buildTemplate());

        int firstSize = quizAttemptController.answersBox.getChildren().size();

        selectAnswer("A1");
        quizAttemptController.handleNext();

        int secondSize = quizAttemptController.answersBox.getChildren().size();

        assertEquals(firstSize, secondSize);
    }

    @Test
    void checkCompletion() {
        // Checks that the quiz does not go beyond the last question
        quizAttemptController.initQuiz(buildTemplate());

        selectAnswer("A1");
        quizAttemptController.handleNext(); // Q2

        selectAnswer("B1");
        quizAttemptController.handleNext(); // finish

        int finalIndex = quizAttemptController.getQuestionNumber();

        quizAttemptController.handleNext();

        assertEquals(finalIndex, quizAttemptController.getQuestionNumber());
    }

    @Test
    void completionStopsProgression() {
        quizAttemptController.initQuiz(buildTemplate());

        selectAnswer("A1");
        quizAttemptController.handleNext(); // Q2

        selectAnswer("B1");
        quizAttemptController.handleNext(); // finish

        int finalState = quizAttemptController.getQuestionNumber();

        quizAttemptController.handleNext();

        assertEquals(finalState, quizAttemptController.getQuestionNumber());
    }

    @Test
    void completionCannotAdvance() {
        // Checks user cannot advance past the end of the quiz
        quizAttemptController.initQuiz(buildTemplate());

        selectAnswer("A1");
        quizAttemptController.handleNext();

        selectAnswer("B1");
        quizAttemptController.handleNext();

        int finalState = quizAttemptController.getQuestionNumber();

        quizAttemptController.handleNext();

        assertEquals(finalState, quizAttemptController.getQuestionNumber());
    }

    private void selectAnswer(String text) {
        for (javafx.scene.Node node : quizAttemptController.answersBox.getChildren()) {
            if (node instanceof RadioButton radioButton && radioButton.getText().equals(text)) {
                radioButton.setSelected(true);
                return;
            }
        }
    }
}
