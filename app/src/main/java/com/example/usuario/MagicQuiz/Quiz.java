package com.example.usuario.MagicQuiz;

import java.util.ArrayList;

public class Quiz {

    private String question;
    private ArrayList<String> answers;
    private int choice = -1;
    private boolean chosed = false;

    public Quiz(String question, ArrayList<String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public Quiz(String question) {
        this.question = question;
        this.answers = new ArrayList<String>();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        setQuestionAndAnswers(question, this.answers);
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        setQuestionAndAnswers(this.question, answers);
    }

    public void setQuestionAndAnswers(String question, ArrayList<String> answers) {
        this.question = question;
        this.answers = answers;
        this.choice = -1;
        this.chosed = false;
    }

    public void addAnswer(String answer) {
        this.answers.add(answer);
    }

    public int getChoice() {
        return choice;
    }

    public boolean setChoice(int choice) {
        if(choice >= 0 && choice < answers.size()) {
            this.choice = choice;
            this.chosed = true;
            return true;
        }
        return false;
    }

    public boolean isChosed() {
        return chosed;
    }

    public void unchose() {
        this.choice = -1;
        this.chosed = false;
    }
}
