package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class QuizAttemptServiceTest {


    private StubAttemptDAO attemptDAO;
    private StubAttemptQuestionDAO questionDAO;
    private StubAttemptAnswerDAO answerDAO;
    private QuizAttemptService service;

    private QuizTemplate template;

    private Map<Integer, QuizTemplateAnswer> allCorrect;
    private Map<Integer, QuizTemplateAnswer> allIncorrect;
    private Map<Integer, QuizTemplateAnswer> mixedSelections; // 2 correct, 1 wrong

    @BeforeEach
    void setUp() {
        attemptDAO   = new StubAttemptDAO();
        questionDAO  = new StubAttemptQuestionDAO();
        answerDAO    = new StubAttemptAnswerDAO();
        service      = new QuizAttemptService(attemptDAO, questionDAO, answerDAO);

        template = new QuizTemplate("Test Quiz", 1, 1, "published");
        template.setId(10);

        allCorrect   = new HashMap<>();
        allIncorrect = new HashMap<>();
        mixedSelections = new HashMap<>();

        for (int i = 0; i < 3; i++) {
            int qId = i + 1;
            QuizTemplateQuestion q = new QuizTemplateQuestion(template.getId(), "Question " + qId);
            q.setId(qId);

            QuizTemplateAnswer correct   = new QuizTemplateAnswer(qId, "Right " + qId,   true);
            QuizTemplateAnswer incorrect = new QuizTemplateAnswer(qId, "Wrong " + qId, false);
            correct.setId(qId * 10);
            incorrect.setId(qId * 10 + 1);

            q.addAnswer(correct);
            q.addAnswer(incorrect);
            template.addQuestion(q);

            allCorrect.put(qId, correct);
            allIncorrect.put(qId, incorrect);
            // questions 1 & 2 correct, question 3 wrong
            mixedSelections.put(qId, i < 2 ? correct : incorrect);
        }
    }

    @Test
    void saveAttempt_returnsTotalCount_whenAllCorrect() {
        int userId = 42;

        int result = service.saveAttempt(template, userId, allCorrect);

        assertEquals(3, result, "All 3 answers correct → score should be 3");
    }

    @Test
    void saveAttempt_returnsZero_whenAllIncorrect() {
        int result = service.saveAttempt(template, 42, allIncorrect);

        assertEquals(0, result, "All answers wrong → score should be 0");
    }

    @Test
    void saveAttempt_returnsCorrectCount_whenMixedAnswers() {
        int result = service.saveAttempt(template, 42, mixedSelections);

        assertEquals(2, result, "2 correct, 1 wrong → score should be 2");
    }

    @Test
    void getAttemptsByUser_returnsAttemptForCorrectUser() {
        int userId = 7;

        service.saveAttempt(template, userId, allCorrect);

        List<QuizAttempt> found = service.getAttemptsByUser(userId);

        assertFalse(found.isEmpty(), "There should be at least one attempt for userId " + userId);
        assertTrue(
                found.stream().allMatch(a -> a.getUserId() == userId),
                "All returned attempts must belong to the requested user"
        );
    }

    @Test
    void getAttemptsByUser_doesNotReturnOtherUsersAttempts() {
        service.saveAttempt(template, 1, allCorrect);
        service.saveAttempt(template, 2, allCorrect);

        List<QuizAttempt> user1Attempts = service.getAttemptsByUser(1);

        assertTrue(
                user1Attempts.stream().noneMatch(a -> a.getUserId() == 2),
                "Attempts for user 2 must not appear in user 1's results"
        );
    }

    // stub DAOs

    // Isolated in memory stub uses instance fields (not static) for test safety. */
    private static class StubAttemptDAO implements IQuizAttemptDAO {
        private final List<QuizAttempt> store = new ArrayList<>();
        private int nextId = 1;

        @Override public void add(QuizAttempt a) { a.setId(nextId++); store.add(a); }
        @Override public void update(QuizAttempt a) {}
        @Override public void delete(QuizAttempt a) { store.remove(a); }
        @Override public void deleteByUserId(int userId) { store.removeIf(x -> x.getUserId() == userId); }
        @Override public List<QuizAttempt> getAll() { return new ArrayList<>(store); }
        @Override public QuizAttempt get(int id) {
            return store.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
        }
        @Override public List<QuizAttempt> getByUserId(int userId) {
            return store.stream().filter(a -> a.getUserId() == userId).toList();
        }
        @Override public QuizAttempt getLatestByUserId(int userId) {
            return store.stream()
                    .filter(a -> a.getUserId() == userId)
                    .reduce((first, second) -> second)
                    .orElse(null);
        }
    }

    private static class StubAttemptQuestionDAO implements IQuizAttemptQuestionDAO {
        private final List<QuizAttemptQuestion> store = new ArrayList<>();
        private int nextId = 1;

        @Override public void add(QuizAttemptQuestion q) { q.setId(nextId++); store.add(q); }
        @Override public void update(QuizAttemptQuestion q) {}
        @Override public void delete(QuizAttemptQuestion q) { store.remove(q); }
        @Override public QuizAttemptQuestion get(int id) { return store.stream().filter(x -> x.getId() == id).findFirst().orElse(null); }
        @Override public List<QuizAttemptQuestion> getByQuizAttemptId(int attemptId) {
            return store.stream().filter(q -> q.getQuizAttemptId() == attemptId).toList();
        }
    }

    private static class StubAttemptAnswerDAO implements IQuizAttemptAnswerDAO {
        private final List<QuizAttemptAnswer> store = new ArrayList<>();
        private int nextId = 1;

        @Override public void add(QuizAttemptAnswer a) { a.setId(nextId++); store.add(a); }
        @Override public void delete(QuizAttemptAnswer a) { store.remove(a); }
        @Override public QuizAttemptAnswer get(int id) { return store.stream().filter(x -> x.getId() == id).findFirst().orElse(null); }
        @Override public List<QuizAttemptAnswer> getByQuizAttemptQuestionId(int questionId) {
            return store.stream().filter(a -> a.getQuizAttemptQuestionId() == questionId).toList();
        }
    }
}