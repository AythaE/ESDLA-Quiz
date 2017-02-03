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
import es.aythae.esdlaquiz.model.Question;
import es.aythae.esdlaquiz.model.Questions;

/**
 * @see <a href="http://www.vogella.com/tutorials/AndroidListView/article.html">Reference</a>
 */

public class QuestionsAdapter extends BaseAdapter {

    private final Context context;
    private LayoutInflater inflater;
    private Game game;

    public QuestionsAdapter(Context context, Game game) {
        this.context = context;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.game = game;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.questions_list_row, null);
        }
        TextView questionTV = (TextView) view.findViewById(R.id.question_row);
        TextView responseTV = (TextView) view.findViewById(R.id.response_row);

        int questionId = game.getQuestionID(i);


        Question q = Questions.getQuestionById(questionId);

        questionTV.setText(context.getString(R.string.question_row) + "\t\t" + q.getQuestion());
        responseTV.setText(context.getString(R.string.response_row) + "\t\t" + q.getCorrectAns());

        return view;
    }

    @Override
    public int getCount() {
        return game.getCount();
    }

    @Override
    public Object getItem(int i) {
        int questionId = game.getQuestionID(i);


        return Questions.getQuestionById(questionId);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


}
