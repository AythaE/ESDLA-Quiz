/*
 * File: Results.java
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

/**
 * Class used to store all the game results in memory, it is used to show game statistics. If the
 * user close the app the stored results are deleted.
 */
public class Results {

    private static ArrayList<Game> resultsList = new ArrayList<>();

    public static Game getGame(int i) {
        return resultsList.get(i);
    }

    public static void addNewGame(Game game) {
        resultsList.add(game);
    }

    public static int getCount() {
        return resultsList.size();
    }
}
