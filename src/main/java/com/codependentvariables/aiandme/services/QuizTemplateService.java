package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.database.dao.*;
import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.*;
import java.util.List;
import java.util.Random;

public class QuizTemplateService {
    private static QuizTemplateService instance;

    private final IQuizTemplateDAO quizTemplateDAO;
    private final IQuizTemplateQuestionDAO quizTemplateQuestionDAO;
    private final IQuizTemplateAnswerDAO quizTemplateAnswerDAO;

    private final Random random = new Random();

    private QuizTemplateService(IQuizTemplateDAO quizTemplateDAO, IQuizTemplateQuestionDAO quizTemplateQuestionDAO, IQuizTemplateAnswerDAO quizTemplateAnswerDAO) {
        this.quizTemplateDAO = quizTemplateDAO;
        this.quizTemplateQuestionDAO = quizTemplateQuestionDAO;
        this.quizTemplateAnswerDAO = quizTemplateAnswerDAO;
    }

    public static QuizTemplateService getInstance() {
        if (instance == null) {
            instance = new QuizTemplateService(new SqliteQuizTemplateDAO(), new SqliteQuizTemplateQuestionDAO(), new SqliteQuizTemplateAnswerDAO());
        }
        return instance;
    }

    public static QuizTemplateService createForTest(IQuizTemplateDAO quizTemplateDAO, IQuizTemplateQuestionDAO quizTemplateQuestionDAO, IQuizTemplateAnswerDAO quizTemplateAnswerDAO) {
        instance = new QuizTemplateService(quizTemplateDAO, quizTemplateQuestionDAO, quizTemplateAnswerDAO);
        return instance;
    }

    /**
     * Creates and persists a new quiz template.
     * @throws IllegalArgumentException if the name is blank or categoryId is invalid.
     */
    public QuizTemplate createTemplate(String name, int categoryId, int userId) {
        return createTemplate(name, categoryId, userId, false);
    }

    public QuizTemplate createTemplate(String name, int categoryId, int userId, boolean isPuzzle) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Template name must not be blank.");
        }
        if (categoryId <= 0) {
            throw new IllegalArgumentException("A valid category must be selected.");
        }

        QuizTemplate template = new QuizTemplate(name.trim(), categoryId, userId, isPuzzle, "draft");
        quizTemplateDAO.add(template);
        return template;
    }

    /**
     * Renames an existing template.
     * @throws IllegalArgumentException if the new name is blank.
     */
    public void renameTemplate(QuizTemplate template, String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Template name must not be blank.");
        }
        template.setName(newName.trim());
        quizTemplateDAO.update(template);
    }

    /**
     * Deletes a template and all of its questions and answers.
     */
    public void deleteTemplate(QuizTemplate template) {
        List<QuizTemplateQuestion> questions = quizTemplateQuestionDAO.getByTemplateId(template.getId());
        for (QuizTemplateQuestion q : questions) {
            deleteQuestionAndAnswers(q);
        }
        quizTemplateDAO.delete(template);
    }

    public List<QuizTemplate> getAllTemplates() {
        return quizTemplateDAO.getAll();
    }

    public List<QuizTemplate> getByUserId(int userId) {
        return quizTemplateDAO.getByUserId(userId);
    }

    /**
     * Loads all questions and their answers for a template into its in-memory aggregate.
     * Call this before displaying a template for editing.
     */
    public void loadQuestionsIntoTemplate(QuizTemplate template) {
        List<QuizTemplateQuestion> questions = quizTemplateQuestionDAO.getByTemplateId(template.getId());
        for (QuizTemplateQuestion q : questions) {
            q.setAnswers(quizTemplateAnswerDAO.getByQuestionId(q.getId()));
        }
        template.setQuestions(questions);
    }

    /**
     * Validates and persists the full set of questions and answers,
     * deleting any questions that were removed during the editing session.
     * @throws IllegalArgumentException if any question fails validation.
     */
    public void saveQuestions(List<QuizTemplateQuestion> workingQuestions, List<QuizTemplateQuestion> removedQuestions) {
        for (QuizTemplateQuestion q : workingQuestions) {
            if (q.getText() == null || q.getText().isBlank()) {
                throw new IllegalArgumentException("Every question must have text.");
            }
            long nonBlankAnswers = q.getAnswers().stream()
                    .filter(a -> !a.getText().isBlank()).count();
            boolean hasCorrect = q.getAnswers().stream().anyMatch(QuizTemplateAnswer::isCorrect);
            if (!hasCorrect) {
                throw new IllegalArgumentException("Each question needs at least one correct answer marked.");
            }
        }

        for (QuizTemplateQuestion rq : removedQuestions) {
            deleteQuestionAndAnswers(rq);
        }

        for (QuizTemplateQuestion q : workingQuestions) {
            if (q.getId() == 0) {
                quizTemplateQuestionDAO.add(q);
                for (QuizTemplateAnswer a : q.getAnswers()) {
                    a.setQuizTemplateQuestionId(q.getId());
                    quizTemplateAnswerDAO.add(a);
                }
            } else {
                quizTemplateQuestionDAO.update(q);
                for (QuizTemplateAnswer old : quizTemplateAnswerDAO.getByQuestionId(q.getId())) {
                    quizTemplateAnswerDAO.delete(old);
                }
                for (QuizTemplateAnswer a : q.getAnswers()) {
                    a.setId(0);
                    a.setQuizTemplateQuestionId(q.getId());
                    quizTemplateAnswerDAO.add(a);
                }
            }
        }
    }

    private void deleteQuestionAndAnswers(QuizTemplateQuestion question) {
        for (QuizTemplateAnswer a : quizTemplateAnswerDAO.getByQuestionId(question.getId())) {
            quizTemplateAnswerDAO.delete(a);
        }
        quizTemplateQuestionDAO.delete(question);
    }

    /**
     * Gets any random quiz.
     * @return The random quiz.
     */
    public QuizTemplate getRandomQuiz() {
        List<QuizTemplate> quizTemplates = quizTemplateDAO.getAllQuizzes();
        QuizTemplate quizTemplate = quizTemplates.get(random.nextInt(quizTemplates.size()));
        loadQuestionsIntoTemplate(quizTemplate);
        return quizTemplate;
    }

    /**
     * Gets any random puzzle.
     * @return The random puzzle.
     */
    public QuizTemplate getRandomPuzzle() {
        List<QuizTemplate> quizTemplates = quizTemplateDAO.getAllPuzzles();
        QuizTemplate quizTemplate = quizTemplates.get(random.nextInt(quizTemplates.size()));
        loadQuestionsIntoTemplate(quizTemplate);
        return quizTemplate;
    }
}

