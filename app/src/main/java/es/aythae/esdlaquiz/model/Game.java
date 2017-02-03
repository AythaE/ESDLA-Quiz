package es.aythae.esdlaquiz.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by aythae on 2/02/17.
 */

public class Game implements Serializable {

    private int QUESTIONS_PER_GAME;
    private int correctAnswers;
    private int wrongAnswers;
    private Date date;
    private int[] questions;
    private int counter;


    public Game() {
        correctAnswers = 0;
        wrongAnswers = 0;
        date = new Date();
        QUESTIONS_PER_GAME = Questions.getCount() / 4;
        questions = new int[QUESTIONS_PER_GAME];
        counter = 0;
    }

    public boolean hasGameFinnished() {
        return (correctAnswers + wrongAnswers) >= QUESTIONS_PER_GAME ? true : false;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }


    public void addCorrectAnswer() {
        this.correctAnswers++;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void addWrongAnswer() {
        this.wrongAnswers++;
    }

    public void addGameQuestion(int questionID) {
        if (counter < QUESTIONS_PER_GAME)
            questions[counter++] = questionID;
    }

    public int getCount(){
        return counter;
    }
    public double getCorrectPercent() {
        double percent;
        if ((correctAnswers + wrongAnswers) > 0) {
            percent = ((double) correctAnswers / (correctAnswers + wrongAnswers)) * 100;
        } else {
            percent = 0;
        }
        return percent;
    }

    public Date getDate() {
        return date;
    }

    public int getQuestionID(int i) {
        return questions[i];
    }
}
