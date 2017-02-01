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


public class Question {
    public enum QuestionType {TEXT, IMAGE, SOUND}

    private int id;
    private QuestionType type;
    private String question;
    private String correctAns;
    private String wrongAns1;
    private String wrongAns2;
    private String wrongAns3;
    private String resource;

    public Question() {
    }

    public Question(int id, int type, String question, String correctAns, String wrongAns1, String wrongAns2, String wrongAns3, String resource) {
        this.id = id;
        this.type = QuestionType.values()[type];
        this.question = question;
        this.correctAns = correctAns;
        this.wrongAns1 = wrongAns1;
        this.wrongAns2 = wrongAns2;
        this.wrongAns3 = wrongAns3;
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", type=" + type +
                ", question='" + question + '\'' +
                ", correctAns='" + correctAns + '\'' +
                ", wrongAns1='" + wrongAns1 + '\'' +
                ", wrongAns2='" + wrongAns2 + '\'' +
                ", wrongAns3='" + wrongAns3 + '\'' +
                ", resource='" + resource + '\'' +
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

    public void setType(QuestionType type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    public String getWrongAns1() {
        return wrongAns1;
    }

    public void setWrongAns1(String wrongAns1) {
        this.wrongAns1 = wrongAns1;
    }

    public String getWrongAns2() {
        return wrongAns2;
    }

    public void setWrongAns2(String wrongAns2) {
        this.wrongAns2 = wrongAns2;
    }

    public String getWrongAns3() {
        return wrongAns3;
    }

    public void setWrongAns3(String wrongAns3) {
        this.wrongAns3 = wrongAns3;
    }
}
