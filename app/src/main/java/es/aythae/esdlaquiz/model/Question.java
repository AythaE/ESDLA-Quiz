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
    public enum QuestionType {TEXT, IMAGE}

    private int id;
    private QuestionType type;
    private String question;
    private String correctAns;
    private String wrongAns1;
    private String wrongAns2;
    private String wrongAns3;
    private String [] answers = new String[4];
    private String resource;


    public Question(int id, int type, String question, String correctAns, String wrongAns1, String wrongAns2, String wrongAns3, String resource) {
        this.id = id;
        this.type = QuestionType.values()[type];
        this.question = question;
        this.correctAns = this.answers[0] = correctAns;
        this.wrongAns1 = this.answers[1] = wrongAns1;
        this.wrongAns2 = this.answers[2] = wrongAns2;
        this.wrongAns3 = this.answers[3] = wrongAns3;
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

    public String getQuestion() {
        return question;
    }


    public String getCorrectAns() {
        return correctAns;
    }

    public String getResource() {
        return resource;
    }




    public String getWrongAns1() {
        return wrongAns1;
    }


    public String getWrongAns2() {
        return wrongAns2;
    }


    public String getWrongAns3() {
        return wrongAns3;
    }


    public String getAnswer(int index){
        return answers[index];
    }
}
