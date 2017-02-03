package es.aythae.esdlaquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import es.aythae.esdlaquiz.adapters.ResultsAdapter;

public class ResultsActivity extends AppCompatActivity {

    private ResultsAdapter resultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultsAdapter = new ResultsAdapter(this);
        ListView listView = (ListView) findViewById(R.id.results_list);
        listView.setAdapter(resultsAdapter);
    }


}
