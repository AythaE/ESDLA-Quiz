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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import java.util.ArrayList;

import es.aythae.esdlaquiz.DB.DBHelper;
import es.aythae.esdlaquiz.model.Question;
import es.aythae.esdlaquiz.model.Questions;

/**
 * asdasdasd
 */
public class MainActivity extends AppCompatActivity {

    private WebView webView= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(this.getClass().getSimpleName(), "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readDB();

        Button gameBtn = (Button) findViewById(R.id.button_play);
        Button resultsBtn = (Button) findViewById(R.id.button_stats);
        Button othersBtn = (Button) findViewById(R.id.button_others);

        gameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameState.initialize();
                startActivity(new Intent(MainActivity.this, GameActivity.class));
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

/*
                webView = new WebView(MainActivity.this);
                webView.getSettings().getJavaScriptEnabled();
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl("http://www.juegos.com/juegos/juegos-de-preguntas");

                setContentView(webView);
*/
                startActivity(new Intent(MainActivity.this, WebViewActivity.class));
            }
        });


    }


    private void readDB() {
        DBHelper dbHelper = DBHelper.getInstance(this);


        ArrayList<Question> questions = dbHelper.getQuestions();

        Log.d(this.getClass().getSimpleName(), "ReadDB: number of questions: "+questions.size());

        Questions.setQuestionsList(questions);
    }


}
