/*
 * File: ResultsActivity.java
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
import android.widget.ListView;

import es.aythae.esdlaquiz.adapters.ResultsAdapter;

/**
 * Class that handle the statistics UI loading the components and assigning a
 * listView adapter to populate the list with all the available results
 */
public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ResultsAdapter resultsAdapter = new ResultsAdapter(this);
        ListView listView = (ListView) findViewById(R.id.results_list);
        listView.setAdapter(resultsAdapter);
    }


}
