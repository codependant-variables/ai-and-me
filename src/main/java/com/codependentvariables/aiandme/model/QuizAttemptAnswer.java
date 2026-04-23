package com.codependentvariables.aiandme.model;

public class QuizAttemptAnswer {
    private int id;
    private int quizAttemptQuestionId;
    private String text;
    private byte[] image;
    private boolean isCorrect;

    public QuizAttemptAnswer(int quizAttemptQuestionId, String text, byte[] image, boolean isCorrect) {
        this.quizAttemptQuestionId = quizAttemptQuestionId;
        this.text = text;
        this.image = image;
        this.isCorrect = isCorrect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuizAttemptQuestionId() {
        return quizAttemptQuestionId;
    }

    public void setQuizAttemptQuestionId(int quizAttemptQuestionId) {
        this.quizAttemptQuestionId = quizAttemptQuestionId;
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

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}