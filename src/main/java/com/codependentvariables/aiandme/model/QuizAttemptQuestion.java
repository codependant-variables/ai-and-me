package com.codependentvariables.aiandme.model;

import com.codependentvariables.aiandme.model.dao.BaseSqliteDAO;

public class QuizAttemptQuestion {
    private int id;
    private int quizAttemptId;
    private String text;
    private byte[] image;

    public QuizAttemptQuestion(int quizAttemptId, String text, byte[] image) {
        this.quizAttemptId = quizAttemptId;
        this.text = text;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuizAttemptId() {
        return quizAttemptId;
    }

    public void setQuizAttemptId(int quizAttemptId) {
        this.quizAttemptId = quizAttemptId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
