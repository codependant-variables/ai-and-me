package com.codependentvariables.aiandme.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizTemplateQuestion {
    private int id;
    private int quizTemplateId;
    private String text;
    private byte[] image; // nullable

    /* In-memory aggregate – populated on demand, not persisted directly here. */
    private final List<QuizTemplateAnswer> answers = new ArrayList<>();

    public QuizTemplateQuestion(int quizTemplateId, String text) {
        this.quizTemplateId = quizTemplateId;
        this.text = text;
    }

    public QuizTemplateQuestion(int quizTemplateId, String text, byte[] image) {
        this.quizTemplateId = quizTemplateId;
        this.text = text;
        this.image = image;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getQuizTemplateId() { return quizTemplateId; }
    public void setQuizTemplateId(int quizTemplateId) { this.quizTemplateId = quizTemplateId; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    /* Returns an unmodifiable view of the in-memory answer list. */
    public List<QuizTemplateAnswer> getAnswers() {
        return Collections.unmodifiableList(answers);
    }

    /**
     * Adds an answer to the in-memory aggregate and ensures its
     * {@code quizTemplateQuestionId} is consistent with this question.
     */
    public void addAnswer(QuizTemplateAnswer answer) {
        answer.setQuizTemplateQuestionId(this.id);
        answers.add(answer);
    }

    /* Removes an answer from the in-memory aggregate. */
    public void removeAnswer(QuizTemplateAnswer answer) {
        answers.remove(answer);
    }

    /*
     * Replaces the entire in-memory answer list (e.g. after loading from DAO).
     */
    public void setAnswers(List<QuizTemplateAnswer> answers) {
        this.answers.clear();
        this.answers.addAll(answers);
    }

    /* Returns {@code true} when at least one answer is marked correct. */
    public boolean hasCorrectAnswer() {
        return answers.stream().anyMatch(QuizTemplateAnswer::isCorrect);
    }

    /**
     * Returns {@code true} when this question is structurally valid:
     * <ul>
     *   <li>Has non-blank question text</li>
     *   <li>Has at least 2 answers</li>
     *   <li>Has at least 1 correct answer</li>
     * </ul>
     */
    public boolean isValid() {
        if (text == null || text.isBlank()) return false;
        return hasCorrectAnswer();
    }

    @Override
    public String toString() { return text; }
}
