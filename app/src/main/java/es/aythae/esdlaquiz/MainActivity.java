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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import es.aythae.esdlaquiz.DB.DBHelper;
import es.aythae.esdlaquiz.model.Game;
import es.aythae.esdlaquiz.model.Question;
import es.aythae.esdlaquiz.model.Questions;

/**
 * Class that handle the welcome screen of the app. It also read the Database the first time that
 * this activity is created
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(this.getClass().getSimpleName(), "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Questions.areFilled()) {
            readDB();
        }


        Button gameBtn = (Button) findViewById(R.id.button_play);
        Button resultsBtn = (Button) findViewById(R.id.button_stats);
        Button othersBtn = (Button) findViewById(R.id.button_others);

        gameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a new game and send it as an extra to the GameActivity

                Game newGame = new Game();
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                i.putExtra("gameState", newGame);
                startActivity(i);
            }
        });

        resultsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ResultsActivity.class));

            }
        });

        othersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(MainActivity.this, WebViewActivity.class));
            }
        });


    }


    private void readDB() {
        DBHelper dbHelper = DBHelper.getInstance(this);


        ArrayList<Question> questions = dbHelper.getQuestions();

        Log.d(this.getClass().getSimpleName(), "ReadDB: number of questions: " + questions.size());

        Questions.setQuestionsList(questions);
    }


}
