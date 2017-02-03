package es.aythae.esdlaquiz.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by aythae on 3/02/17.
 */

public class Results {

    private static ArrayList<Game> resultsList = new ArrayList<>();

    public static ArrayList<Game> getResultsList() {
        return resultsList;
    }

    public static void setResultsList(ArrayList<Game> _resultsList) {
        resultsList = _resultsList;



    }

    public static Game getLastGame(){
        return resultsList.get(resultsList.size()-1);
    }

    public static Game getGame(int i){
        return resultsList.get(i);
    }
    public static void addNewGame(Game game){
        resultsList.add(game);
    }

    public static int getCount(){
        return resultsList.size();
    }
}
