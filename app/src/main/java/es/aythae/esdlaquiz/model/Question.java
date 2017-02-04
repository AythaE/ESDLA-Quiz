/*
 * File: Question.java
 * Project: ESDLA Quiz
 *
 * Author: Aythami Estévez Olivas
 * Email: aythae[at]gmail[dot]com
 * Date: 31-ene-2017
 * Repository: https://github.com/AythaE/ESDLA-Quiz
 * License: GPL-3.0
 */

package es.aythae.esdlaquiz.model;

import java.util.Arrays;

/**
 * Class that represent a single question on the game, instances of this are retrieved from the
 * SQLite data base
 */
public class Question {
    public enum QuestionType {TEXT, IMAGE, SOUND}

    private int id;
    private QuestionType type;
    private String question;
    private String correctAns;
    private String resource;

    /** Array that store all the answers, it is used to randomly populate the questions options */
    private String [] answers = new String[4];



    public Question(int id, int type, String question, String correctAns, String wrongAns1, String wrongAns2, String wrongAns3, String resource) {
        this.id = id;
        this.type = QuestionType.values()[type];
        this.question = question;
        this.correctAns = this.answers[0] = correctAns;
        this.answers[1] = wrongAns1;
        this.answers[2] = wrongAns2;
        this.answers[3] = wrongAns3;
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", type=" + type +
                ", question='" + question + '\'' +
                ", correctAns='" + correctAns + '\'' +
                ", resource='" + resource + '\'' +
                ", answers=" + Arrays.toString(answers) +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public QuestionType getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public String getResource() {
        return resource;
    }

    public String getAnswer(int index){
        return answers[index];
    }
}
