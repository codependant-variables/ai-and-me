package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.mock.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuizAttemptServiceTest {

    @Test
    void saveAttemptCount() {
        QuizAttemptService service = new QuizAttemptService(new MockQuizAttemptDAO(), new MockQuizAttemptQuestionDAO(), new MockQuizAttemptAnswerDAO(), new MockCategoryDAO());

        QuizTemplate quiz = new QuizTemplate("Quiz", 1, 1, "published");

        QuizTemplateQuestion question1 = new QuizTemplateQuestion(1, "Q1");
        QuizTemplateQuestion question2 = new QuizTemplateQuestion(1, "Q2");
        QuizTemplateQuestion question3 = new QuizTemplateQuestion(1, "Q3");

        question1.setId(1);
        question2.setId(2);
        question3.setId(3);

        quiz.addQuestion(question1);
        quiz.addQuestion(question2);
        quiz.addQuestion(question3);

        QuizTemplateAnswer right1 = new QuizTemplateAnswer(1, "Right", true);
        QuizTemplateAnswer right2 = new QuizTemplateAnswer(2, "Right", true);
        QuizTemplateAnswer wrong3 = new QuizTemplateAnswer(3, "Wrong", false);

        int score = service.saveAttempt(quiz, 42, List.of(right1, right2, wrong3));

        assertEquals(2, score);
    }
}