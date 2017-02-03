package es.aythae.esdlaquiz.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import es.aythae.esdlaquiz.R;
import es.aythae.esdlaquiz.model.Game;
import es.aythae.esdlaquiz.model.Results;

/**
 *
 * @see <a href="http://www.vogella.com/tutorials/AndroidListView/article.html">Reference</a>
 *
 */

public class ResultsAdapter extends BaseAdapter {

    private final Context context;
    private LayoutInflater inflater;

    public ResultsAdapter(Context context){
        this.context = context;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.results_list_row, null);
        }
        TextView dateTV = (TextView)view.findViewById(R.id.date_results);
        TextView correctTV = (TextView)view.findViewById(R.id.correct_questions_results);
        TextView wrongTV = (TextView)view.findViewById(R.id.wrong_questions_results);
        TextView percentTV = (TextView)view.findViewById(R.id.percent_results);

        Game game = Results.getGame(i);

        dateTV.setText(new SimpleDateFormat("dd/MM/yyyy 'a las' HH:mm:ss").format(game.getDate()));
        correctTV.setText(context.getString(R.string.results_dialog_correct_questions) +"\t\t"+ game.getCorrectAnswers());
        wrongTV.setText(context.getString(R.string.results_dialog_wrong_questions) +"\t\t\t"+ game.getWrongAnswers());
        double percent = game.getCorrectPercent();
        percentTV.setText(context.getString(R.string.results_dialog_percent_questions) +"\t\t"+ String.format("%.2f", percent)
                + context.getString(R.string.percent));


        return view;
    }
    @Override
    public int getCount() {
        return Results.getCount();
    }

    @Override
    public Object getItem(int i) {
        return Results.getGame(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


}
