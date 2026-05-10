package com.codependentvariables.aiandme.controllers;
import com.codependentvariables.aiandme.controller.QuizAttemptController;
import com.codependentvariables.aiandme.model.QuizTemplate;
import com.codependentvariables.aiandme.model.QuizTemplateQuestion;
import com.codependentvariables.aiandme.model.QuizTemplateAnswer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizAttemptControllerTest {

    private QuizTemplate template;

    @BeforeEach
    public void setupQuiz() {
        // 1.1 Create a template
        template = new QuizTemplate("Test quiz", 1, 1, "draft");
        template.setId(1);

        // 1.2 Build questions and answers for this example
        QuizTemplateQuestion q1 = new QuizTemplateQuestion(1, "Why is it night time?");
        q1.setId(1);
        q1.addAnswer(new QuizTemplateAnswer(1, "Because the sun is on the other side of the world.", true));
        q1.addAnswer(new QuizTemplateAnswer(2, "Because the sun is tired.", false));

        QuizTemplateQuestion q2 = new QuizTemplateQuestion(2, "Why is it day time?");
        q2.setId(2);
        q2.addAnswer(new QuizTemplateAnswer(3, "Because the sun is shining on our side of the world.", true));
        q2.addAnswer(new QuizTemplateAnswer(4, "Because the sun is awake.", false));

        // 1.3 Add questions to template
        template.addQuestion(q1);
        template.addQuestion(q2);
    }

    // 1 - Check a questions load
    @Test
    void checkQuestionsLoad() {
        List<QuizTemplateQuestion> questions = template.getQuestions();
        assertEquals(2, questions.size());
    }

    @Test
    void checkAnswersLoad() {
        List<QuizTemplateQuestion> questions = template.getQuestions();
        QuizTemplateQuestion q1 = questions.get(0);
        QuizTemplateQuestion q2 = questions.get(1);

        assertEquals(2, q1.getAnswers().size());
        assertEquals(2, q2.getAnswers().size());
    }

    // 2 Check that a loaded quiz is being attempted loads the next question and answers properly.



    // 3 - Going to the next question works
    /*
    @Test
    void testHandleNext() {
        fail("Not implemented.");
    }
    */
    // 4 - At the last question, prompt for completion
    /*
    @Test
    void testFinishQuiz() {
        fail("Not implemented.");
    }
    */
}
