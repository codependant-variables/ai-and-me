package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizTemplateService {

    private static QuizTemplateService instance;

    private final IQuizTemplateDAO templateDAO;
    private final IQuizTemplateQuestionDAO questionDAO;
    private final IQuizTemplateAnswerDAO answerDAO;

    private QuizTemplateService() {
        this(new SqliteQuizTemplateDAO(),
             new SqliteQuizTemplateQuestionDAO(),
             new SqliteQuizTemplateAnswerDAO());
    }

    // Package-private constructor for unit tests
    QuizTemplateService(IQuizTemplateDAO templateDAO,
                        IQuizTemplateQuestionDAO questionDAO,
                        IQuizTemplateAnswerDAO answerDAO) {
        this.templateDAO = templateDAO;
        this.questionDAO = questionDAO;
        this.answerDAO   = answerDAO;
    }

    public static QuizTemplateService getInstance() {
        if (instance == null) {
            instance = new QuizTemplateService();
        }
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
        templateDAO.add(template);
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
        templateDAO.update(template);
    }

    /**
     * Deletes a template and all of its questions and answers.
     */
    public void deleteTemplate(QuizTemplate template) {
        List<QuizTemplateQuestion> questions = questionDAO.getQuestionsByTemplate(template.getId());
        for (QuizTemplateQuestion q : questions) {
            deleteQuestionAndAnswers(q);
        }
        templateDAO.delete(template);
    }

    /**
     * Returns the image bytes of the first question for the given template, null otherwise.
     */
    public byte[] getFirstQuestionImage(int templateId) {
        List<QuizTemplateQuestion> questions = questionDAO.getQuestionsByTemplate(templateId);
        if (questions == null || questions.isEmpty()) return null;
        return questions.get(0).getImage();
    }

    public List<QuizTemplate> getAllTemplates() {
        return templateDAO.getAll();
    }

    public List<QuizTemplate> getTemplatesByCategory(int categoryId) {
        return templateDAO.getByCategoryId(categoryId);
    }

    public List<QuizTemplate> getTemplatesByUser(int userId) {
        return templateDAO.getByUserId(userId);
    }

    /**
     * Loads all questions and their answers for a template into its in-memory aggregate.
     * Call this before displaying a template for editing.
     */
    public void loadQuestionsIntoTemplate(QuizTemplate template) {
        List<QuizTemplateQuestion> questions = questionDAO.getQuestionsByTemplate(template.getId());
        for (QuizTemplateQuestion q : questions) {
            q.setAnswers(answerDAO.getAnswersByQuestion(q.getId()));
        }
        template.setQuestions(questions);
    }

    /**
     * Validates and persists the full set of questions and answers for a template,
     * deleting any questions that were removed during the editing session.
     * @throws IllegalArgumentException if any question fails validation.
     */
    public void saveQuestions(QuizTemplate template,
                              List<QuizTemplateQuestion> workingQuestions,
                              List<QuizTemplateQuestion> removedQuestions) {

        for (QuizTemplateQuestion q : workingQuestions) {
            if (q.getText() == null || q.getText().isBlank()) {
                throw new IllegalArgumentException("Every question must have text.");
            }
            long nonBlankAnswers = q.getAnswers().stream()
                    .filter(a -> !a.getText().isBlank()).count();
            if (nonBlankAnswers < 2) {
                throw new IllegalArgumentException("Each question needs at least 2 answers.");
            }
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
                questionDAO.addQuestion(q);
                for (QuizTemplateAnswer a : q.getAnswers()) {
                    a.setQuizTemplateQuestionId(q.getId());
                    answerDAO.addAnswer(a);
                }
            } else {
                questionDAO.updateQuestion(q);
                for (QuizTemplateAnswer old : answerDAO.getAnswersByQuestion(q.getId())) {
                    answerDAO.deleteAnswer(old);
                }
                for (QuizTemplateAnswer a : q.getAnswers()) {
                    a.setId(0);
                    a.setQuizTemplateQuestionId(q.getId());
                    answerDAO.addAnswer(a);
                }
            }
        }
    }

    private void deleteQuestionAndAnswers(QuizTemplateQuestion question) {
        for (QuizTemplateAnswer a : answerDAO.getAnswersByQuestion(question.getId())) {
            answerDAO.deleteAnswer(a);
        }
        questionDAO.deleteQuestion(question);
    }

    public QuizTemplate getRandomTemplate() {

        List<QuizTemplate> templates = templateDAO.getAll();
        List<QuizTemplate> validTemplates = new ArrayList<>();

        for (QuizTemplate template : templates) {

            // Skip puzzles
            if (template.isPuzzle()) {
                continue;
            }

            loadQuestionsIntoTemplate(template);

            if (template.getQuestions() != null && template.getQuestions().size() >= 2) {

                validTemplates.add(template);
            }
        }

        if (validTemplates.isEmpty()) {
            return null;
        }

        Random random = new Random();

        return validTemplates.get(
                random.nextInt(validTemplates.size())
        );
    }

    public QuizTemplate getRandomPuzzle() {

        List<QuizTemplate> templates = templateDAO.getAll();
        List<QuizTemplate> validPuzzles = new ArrayList<>();

        for (QuizTemplate template : templates) {

            // Only puzzles
            if (!template.isPuzzle()) {
                continue;
            }

            // IMPORTANT
            loadQuestionsIntoTemplate(template);

            if (template.getQuestions() != null &&
                    template.getQuestions().size() >= 2) {

                validPuzzles.add(template);
            }
        }

        if (validPuzzles.isEmpty()) {
            return null;
        }

        Random random = new Random();

        return validPuzzles.get(
                random.nextInt(validPuzzles.size())
        );
    }
}

