package mtg.judge.ipgtree;

import java.util.ArrayList;

public class Quiz {

    //region variables

    private String question;
    private ArrayList<String> answers;
    private int correctAnswerPosition;
    private int chosenAnswerPosition = -1;
    private String resource = null;

    //endregion

    //region constructors

    public Quiz(String question, ArrayList<String> answers) {
        this.question = question;
        this.answers = answers;
        this.setNoCorrectAnswer();
        this.setNoChosenAnswer();
    }

    public Quiz(String question, int correctAnswerPosition, ArrayList<String> answers) {
        this.question = question;
        this.answers = answers;
        this.correctAnswerPosition = correctAnswerPosition;
        this.setNoChosenAnswer();
    }

    public Quiz(String question, ArrayList<String> answers, int defaultAnswerPosition) {
        this.question = question;
        this.answers = answers;
        this.setNoCorrectAnswer();
        this.chosenAnswerPosition = defaultAnswerPosition;
    }

    public Quiz(String question, int correctAnswerPosition, ArrayList<String> answers, int defaultAnswerPosition) {
        this.question = question;
        this.answers = answers;
        this.correctAnswerPosition = correctAnswerPosition;;
        this.chosenAnswerPosition = defaultAnswerPosition;
    }

    public Quiz(String question, ArrayList<String> answers, String resource) {
        this.question = question;
        this.answers = answers;
        this.setNoCorrectAnswer();
        this.setNoChosenAnswer();
        this.resource = resource;
    }

    public Quiz(String question, int correctAnswerPosition, ArrayList<String> answers, String resource) {
        this.question = question;
        this.answers = answers;
        this.correctAnswerPosition = correctAnswerPosition;
        this.setNoChosenAnswer();
        this.resource = resource;
    }

    public Quiz(String question, ArrayList<String> answers, int defaultAnswerPosition, String resource) {
        this.question = question;
        this.answers = answers;
        this.setNoCorrectAnswer();
        this.chosenAnswerPosition = defaultAnswerPosition;
        this.resource = resource;
    }

    public Quiz(String question, int correctAnswerPosition, ArrayList<String> answers, int defaultAnswerPosition, String resource) {
        this.question = question;
        this.answers = answers;
        this.correctAnswerPosition = correctAnswerPosition;;
        this.chosenAnswerPosition = defaultAnswerPosition;
        this.resource = resource;
    }

    //endregion

    //region gettersAndSetters

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
        this.setNoCorrectAnswer();
        this.setNoChosenAnswer();
    }

    public void setAnswers(int correctAnswerPosition, ArrayList<String> answers) {
        this.answers = answers;
        this.correctAnswerPosition = correctAnswerPosition;
        this.setNoChosenAnswer();
    }

    public void setAnswers(ArrayList<String> answers, int defaultAnswerPosition) {
        this.answers = answers;
        this.setNoCorrectAnswer();
        this.chosenAnswerPosition = defaultAnswerPosition;
    }

    public void setAnswers(int correctAnswerPosition, ArrayList<String> answers, int defaultAnswerPosition) {
        this.answers = answers;
        this.correctAnswerPosition = correctAnswerPosition;
        this.chosenAnswerPosition = defaultAnswerPosition;
    }

    public String getAnswer(int position) {
        return answers.get(position);
    }

    public void setAnswer(int position, String answer) {
        if(existAnswer(position)) {
            this.answers.set(position, answer);
        }
    }

    public int getCorrectAnswerPosition() {
        return correctAnswerPosition;
    }

    public boolean setCorrectAnswerPosition(int position) {
        if (existAnswer(position)) {
            this.correctAnswerPosition = position;
            return true;
        }
        else return false;
    }

    public String getCorrectAnswer() {
        return answers.get(correctAnswerPosition);
    }

    public void setNoCorrectAnswer() {
        this.correctAnswerPosition = -1;
    }

    public int getChosenAnswerPosition() {
        return chosenAnswerPosition;
    }

    public boolean setChosenAnswerPosition(int position) {
        if (existAnswer(position)) {
            this.chosenAnswerPosition = position;
            return true;
        }
        else return false;
    }

    public String getChosenAnswer() {
        return this.answers.get(chosenAnswerPosition);
    }

    public void setNoChosenAnswer() {
        this.chosenAnswerPosition = -1;
    }

    public String getResource() {
        return this.resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    //endregion

    //region others

    public boolean existAnswer(int position) {
        return 0 <= position && position < this.numberOfAnswers();
    }

    public int numberOfAnswers() {
        return this.answers.size();
    }

    public void addAnswer(String answer) {
        this.answers.add(answer);
        this.setNoChosenAnswer();
    }

    public boolean removeAnswer(int position) {
        if(existAnswer(position)) {
            answers.remove(position);
            this.setNoChosenAnswer();
            return true;
        }
        else return false;
    }

    public boolean isAnswered() {
        return chosenAnswerPosition != -1;
    }

    public boolean hasCorrectAnswer() {
        return correctAnswerPosition != -1;
    }

    public boolean hasAnswers() {
        return numberOfAnswers() > 0;
    }

    //endregion
}






