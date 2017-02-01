/*
 * File: MainActivity.java
 * Project: ESDLA Quiz
 *
 * Author: Aythami Estévez Olivas
 * Email: aythae[at]gmail[dot]com
 * Date: 31-ene-2017
 * Repository: https://github.com/AythaE/ESDLA-Quiz
 * License: GPL-3.0
 */

package es.aythae.esdlaquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import es.aythae.esdlaquiz.DB.DBHelper;
import es.aythae.esdlaquiz.model.Question;

/**
 * asdasdasd
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this);


        ArrayList<Question> questions = dbHelper.getQuestions();

        Log.d(this.getClass().getSimpleName(), "ReadDB: "+questions.toString());
    }


}
