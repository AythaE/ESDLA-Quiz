package es.aythae.esdlaquiz;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import es.aythae.esdlaquiz.model.Question;
import es.aythae.esdlaquiz.model.Questions;

public class GameActivity extends AppCompatActivity {

    private Button[] buttons = new Button[4];
    private ImageView imageView;
    private TextView questionView;
    private SoundPool soundPool;
    private int correctSound, wrongSound;
    private Question actualQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Log.d(this.getClass().getSimpleName(), "onCreate: ");

        buttons[0] = (Button) findViewById(R.id.response_btn1);
        buttons[1] = (Button) findViewById(R.id.response_btn2);
        buttons[2] = (Button) findViewById(R.id.response_btn3);
        buttons[3] = (Button) findViewById(R.id.response_btn4);

        imageView = (ImageView) findViewById(R.id.question_image);
        questionView = (TextView) findViewById(R.id.question);

        //Reference: http://www.edumobile.org/android/sound-pool-example-in-android-development/
        //Reference: http://stackoverflow.com/a/27552576/6441806
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           createNewSoundPool();
        } else {
            createOldSoundPool();
        }


        Questions.shuffle();
        actualQuestion = Questions.getQuestion();

        //TODO a maximum number of questions (15 for example) to finish the game
        loadQuestion();

        //TODO print results at the end with the correct answers
        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerSelection(0);
            }
        });
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerSelection(1);
            }
        });
        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerSelection(2);
            }
        });
        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerSelection(3);
            }
        });
    }

    private void answerSelection(int btnIndex) {
        String answer = buttons[btnIndex].getText().toString();

        if (answer.equals(actualQuestion.getCorrectAns())){
            //Correct response
            //TODO display a dialog and closeit after the sound has finish
            playSound(correctSound);

            //TODO retrieve a new question and load it
        }
        else{
            //Wrong response
            //TODO display a dialog and with the option of quit game and see correct answer
            //TODO or continue till the end and see the correct answers then

            playSound(wrongSound);


        }

        //TODO Save the results
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool(){
        Log.d(this.getClass().getSimpleName(), "onCreate: New soundpool constructor");

        AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();

        soundPool = new SoundPool.Builder().setMaxStreams(2).setAudioAttributes(attr).build();
        correctSound = soundPool.load(this,R.raw.correct_sound,1);
        wrongSound = soundPool.load(this,R.raw.wrong_sound,1);
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool(){
        Log.d(this.getClass().getSimpleName(), "onCreate: old soundpool constructor");

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        correctSound = soundPool.load(this,R.raw.correct_sound,1);
        wrongSound = soundPool.load(this,R.raw.wrong_sound,1);
    }
    /**
     *
     * @param soundId
     * @see <a href="http://www.edumobile.org/android/sound-pool-example-in-android-development/">Reference</a>
     */
    private void playSound (int soundId){
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = actualVolume / maxVolume;

        soundPool.play(soundId,volume,volume,1,0,1f);
    }
    private void loadQuestion() {



        Log.d(this.getClass().getSimpleName(), "loadQuestion: loading actualQuestion");
        questionView.setText(actualQuestion.getQuestion());

        boolean[] ocupiedButtons = new boolean[4];


        Random rand = new Random();

        int btnNum;
        for (int i = 0; i < 4; i++) {

            btnNum = rand.nextInt(4);

            if (ocupiedButtons[btnNum] == false) {
                buttons[btnNum].setText(actualQuestion.getAnswer(i));
                ocupiedButtons[btnNum] = true;
            } else
                i--;
        }

        if (actualQuestion.getType() == Question.QuestionType.IMAGE) {
            imageView.setImageResource(getResources().getIdentifier("drawable/" + actualQuestion.getResource(), null, getPackageName()));
            imageView.setVisibility(View.VISIBLE);
        } else if (actualQuestion.getType() == Question.QuestionType.SOUND) {
            //TODO use mediaplayer to play sounds and create buttons to control reproduction
            //TODO Play, stop and reset
        }
    }
}
