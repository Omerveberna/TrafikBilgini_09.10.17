package com.example.omerveberna.trafikbilgini;

/**
 * Created by Omerveberna on 19.07.2017.
 */

public class Question {

    private String questionText;
    private String choice_a;
    private String choice_b;
    private String correctAnswer;
    private String correctAnswerText;





    public Question() {
    }

    public Question(String questionText, String choice_a, String choice_b, String choice_c, String choice_d, String choice_e, String correctAnswer, String correctAnswerText) {
        this.questionText = questionText;
        this.choice_a = choice_a;
        this.choice_b = choice_b;
        this.correctAnswer = correctAnswer;
        this.correctAnswerText= correctAnswerText;
    }

    public String getCorrectAnswerText() { return correctAnswerText; }

    public void setCorrectAnswerText(String correctAnswerText) { this.correctAnswerText = correctAnswerText; }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getChoice_a() {
        return choice_a;
    }

    public void setChoice_a(String choice_a) {
        this.choice_a = choice_a;
    }

    public String getChoice_b() {
        return choice_b;
    }

    public void setChoice_b(String choice_b) {
        this.choice_b = choice_b;
    }


    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public boolean isCorrectAnswer(String selectedAnswerText){

        return selectedAnswerText.matches(correctAnswer);

    }

}
