package es.aythae.esdlaquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

import es.aythae.esdlaquiz.model.Question;
import es.aythae.esdlaquiz.model.Questions;

public class GameActivity extends AppCompatActivity {

    private Button [] buttons = new Button[4];
    private ImageView imageView;
    private TextView questionView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        buttons[0] = (Button) findViewById(R.id.response_btn1);
        buttons[1] = (Button) findViewById(R.id.response_btn2);
        buttons[2] = (Button) findViewById(R.id.response_btn3);
        buttons[3] = (Button) findViewById(R.id.response_btn4);

        imageView = (ImageView) findViewById(R.id.question_image);

        questionView = (TextView) findViewById(R.id.question);

        Questions.shuffle();
    }

    private void loadQuestion(){
        Question q = Questions.getQuestion();

        questionView.setText(q.getQuestion());

        boolean [] ocupiedButtons = new boolean[4];

        Arrays.fill(ocupiedButtons,false);

        Random rand = new Random();

        int btnNum;
        for (int i=0; i<4; i++) {
            do {
                btnNum = rand.nextInt(4);

                if (ocupiedButtons[btnNum] == false){
                    buttons[btnNum].setText(q.getAnswer(i));
                    ocupiedButtons[btnNum] = true;
                }
            } while (ocupiedButtons[btnNum]);
        }

        if (q.getType() == Question.QuestionType.IMAGE){
            imageView.setImageResource(getResources().getIdentifier("drawable/"+q.getResource(),null, getPackageName()));
            imageView.setVisibility(View.VISIBLE);
        }
        else if (q.getType() == Question.QuestionType.SOUND){

        }
    }
}
