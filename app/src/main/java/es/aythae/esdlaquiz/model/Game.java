/*
 * File: Game.java
 * Project: ESDLA Quiz
 *
 * Author: Aythami Estévez Olivas
 * Email: aythae[at]gmail[dot]com
 * Date: 31-ene-2017
 * Repository: https://github.com/AythaE/ESDLA-Quiz
 * License: GPL-3.0
 */
package es.aythae.esdlaquiz.model;

import java.io.Serializable;
import java.util.Date;


/**
 * Class Game that store the state of a single game
 */
public class Game implements Serializable {

    private int QUESTIONS_PER_GAME;
    private int correctAnswers;
    private int wrongAnswers;
    private Date date;
    /**
     * Array that store the ids of this game questions to show the correct answers to the user at
     * the end
     */
    private int[] questions;
    /** Count the number of asked questions */
    private int counter;


    public Game() {
        correctAnswers = 0;
        wrongAnswers = 0;
        date = new Date();
        QUESTIONS_PER_GAME = Questions.getCount() / 4;
        questions = new int[QUESTIONS_PER_GAME];
        counter = 0;
    }

    /**
     * Check if the user has answered a number of QUESTIONS_PER_GAME to set the end of this game
     * @return true if the user answer QUESTIONS_PER_GAME questions
     */
    public boolean hasGameFinished() {
        return (correctAnswers + wrongAnswers) >= QUESTIONS_PER_GAME;
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

    /**
     * Add a question id to the questions array
     * @param questionID the id of the asked question
     */
    public void addGameQuestion(int questionID) {
        if (counter < QUESTIONS_PER_GAME)
            questions[counter++] = questionID;
    }

    /**
     * Get the number of asked questions
     * @return the number of asked questions
     */
    public int getCount() {
        return counter;
    }

    /**
     * Gets the percent of correct answers
     * @return the percent
     */
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
