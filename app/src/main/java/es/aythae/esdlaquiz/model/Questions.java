package es.aythae.esdlaquiz.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by aythae on 1/02/17.
 */

public class Questions {


    private static ArrayList<Question> questionsList = null;
    private static Iterator<Question> questionsIterator= null;

    public static ArrayList<Question> getQuestionsList() {
        return questionsList;
    }

    public static void setQuestionsList(ArrayList<Question> _questionsList) {
        questionsList = _questionsList;
        questionsIterator = questionsList.iterator();
    }

    public static Question getQuestion(){
        return questionsIterator.next();
    }
    public static void shuffle(){
        Collections.shuffle(questionsList);
        questionsIterator = questionsList.iterator();
    }
}
