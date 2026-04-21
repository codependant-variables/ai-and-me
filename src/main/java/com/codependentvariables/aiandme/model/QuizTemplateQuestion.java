package com.codependentvariables.aiandme.model;

public class QuizTemplateQuestion {
    private int id;
    private int quizTemplateId;
    private String text;
    private byte[] image; // nullable

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

    @Override
    public String toString() { return text; }
}
