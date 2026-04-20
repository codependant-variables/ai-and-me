package com.codependentvariables.aiandme.model;

import com.codependentvariables.aiandme.model.dao.BaseSqliteDAO;

public class QuizAttemptQuestion {
    private int id;
    private int quizAttemptId;
    private String questionType;
    private String text;
    private byte[] image;

    public QuizAttemptQuestion(int quizAttemptId, String questionType, String text, byte[] image) {
        this.quizAttemptId = quizAttemptId;
        this.questionType = questionType;
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

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
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
