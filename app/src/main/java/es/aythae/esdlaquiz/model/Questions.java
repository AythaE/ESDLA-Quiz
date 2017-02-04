/*
 * File: Questions.java
 * Project: ESDLA Quiz
 *
 * Author: Aythami Estévez Olivas
 * Email: aythae[at]gmail[dot]com
 * Date: 31-ene-2017
 * Repository: https://github.com/AythaE/ESDLA-Quiz
 * License: GPL-3.0
 */

package es.aythae.esdlaquiz.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class used to store all the questions in memory, it has and ArrayList to retrieve and randomly
 * shuffle the questions in different games. It also has a HashMap to could retrieve the questions
 * by ID efficiently
 */
public class Questions {


    private static ArrayList<Question> questionsList = null;
    private static Iterator<Question> questionsIterator= null;
    private static HashMap<Integer,Question> questionHashMap= null;

    public static void setQuestionsList(ArrayList<Question> _questionsList) {
        questionsList = _questionsList;
        questionsIterator = questionsList.iterator();

        questionHashMap = new HashMap<>();
        for (Question q: questionsList) {
            questionHashMap.put(q.getId(),q);
        }

    }

    public static Question getQuestion(){
        return questionsIterator.next();
    }

    public static Question getQuestionById(int id){
        return questionHashMap.get(id);
    }

    public static boolean areFilled(){
        return questionsList != null;
    }
    public static int getCount(){
        return questionsList.size();
    }
    public static void shuffle(){
        Collections.shuffle(questionsList);
        questionsIterator = questionsList.iterator();
    }
}
