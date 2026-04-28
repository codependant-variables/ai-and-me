package com.codependentvariables.aiandme.model;

public class QuizTemplateAnswer {
    private int id;
    private int quizTemplateQuestionId;
    private String text;
    private byte[] image; // nullable
    private boolean isCorrect;

    public QuizTemplateAnswer(int quizTemplateQuestionId, String text, boolean isCorrect) {
        if (text == null || text.isBlank()) throw new IllegalArgumentException("text must not be blank");
        this.quizTemplateQuestionId = quizTemplateQuestionId;
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public QuizTemplateAnswer(int quizTemplateQuestionId, String text, byte[] image, boolean isCorrect) {
        if (text == null || text.isBlank()) throw new IllegalArgumentException("text must not be blank");
        this.quizTemplateQuestionId = quizTemplateQuestionId;
        this.text = text;
        this.image = image;
        this.isCorrect = isCorrect;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getQuizTemplateQuestionId() { return quizTemplateQuestionId; }
    public void setQuizTemplateQuestionId(int quizTemplateQuestionId) { this.quizTemplateQuestionId = quizTemplateQuestionId; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public boolean isCorrect() { return isCorrect; }
    public void setCorrect(boolean correct) { isCorrect = correct; }

    /** Returns {@code true} when this answer has non-blank text. */
    public boolean isValid() {
        return text != null && !text.isBlank();
    }

    @Override
    public String toString() { return text; }
}

