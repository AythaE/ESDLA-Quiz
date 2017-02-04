/*
 * File: QuestionsAdapter.java
 * Project: ESDLA Quiz
 *
 * Author: Aythami Estévez Olivas
 * Email: aythae[at]gmail[dot]com
 * Date: 31-ene-2017
 * Repository: https://github.com/AythaE/ESDLA-Quiz
 * License: GPL-3.0
 */
package es.aythae.esdlaquiz.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import es.aythae.esdlaquiz.R;
import es.aythae.esdlaquiz.model.Game;
import es.aythae.esdlaquiz.model.Question;
import es.aythae.esdlaquiz.model.Questions;

/**
 * Class that extends BaseAdapter and it is used as an adapter for a ListView
 * of questions. This listView is showed to the user at the end of the actual
 * game.
 *
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

    /**
     * Create a view for a single row of the list (one per answered question in
     * the actual game). It show the question and the correct answer of it
     * @param i id of the question
     * @param view view to populate or null if it needs to be created
     * @param viewGroup
     * @return
     */
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
