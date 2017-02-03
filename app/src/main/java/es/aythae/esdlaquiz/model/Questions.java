package es.aythae.esdlaquiz.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by aythae on 1/02/17.
 */

public class Questions {


    private static ArrayList<Question> questionsList = null;
    private static Iterator<Question> questionsIterator= null;
    private static HashMap<Integer,Question> questionHashMap= null;

    public static ArrayList<Question> getQuestionsList() {
        return questionsList;
    }

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
        if (questionsList != null){
            return true;
        }
        else {
            return false;
        }
    }
    public static int getCount(){
        return questionsList.size();
    }
    public static void shuffle(){
        Collections.shuffle(questionsList);
        questionsIterator = questionsList.iterator();
    }
}
