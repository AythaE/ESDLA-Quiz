package es.aythae.esdlaquiz;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Random;

import es.aythae.esdlaquiz.adapters.QuestionsAdapter;
import es.aythae.esdlaquiz.model.Game;
import es.aythae.esdlaquiz.model.Question;
import es.aythae.esdlaquiz.model.Questions;
import es.aythae.esdlaquiz.model.Results;

public class GameActivity extends AppCompatActivity {


    private Button[] buttons = new Button[4];
    private ImageView imageView;
    private TextView questionView;
    private SoundPool soundPool;
    private int correctSound, wrongSound;
    private static Question actualQuestion;
    private static Game gameState;


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

        gameState = (Game) getIntent().getSerializableExtra("gameState");

        Questions.shuffle();


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

    @Override
    public void onBackPressed() {
        printResults();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        Log.d(this.getClass().getSimpleName(), "New soundpool constructor");

        AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();

        soundPool = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(attr).build();
        correctSound = soundPool.load(this, R.raw.correct_sound, 1);
        wrongSound = soundPool.load(this, R.raw.wrong_sound, 1);
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        Log.d(this.getClass().getSimpleName(), "old soundpool constructor");

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        correctSound = soundPool.load(this, R.raw.correct_sound, 1);
        wrongSound = soundPool.load(this, R.raw.wrong_sound, 1);
    }

    /**
     * @param soundId
     * @see <a href="http://www.edumobile.org/android/sound-pool-example-in-android-development/">Reference</a>
     */
    private void playSound(int soundId) {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = actualVolume / maxVolume;

        soundPool.play(soundId, volume, volume, 1, 0, 1f);
    }


    private void loadQuestion() {


        actualQuestion = Questions.getQuestion();

        gameState.addGameQuestion(actualQuestion.getId());

        Log.d(this.getClass().getSimpleName(), "loadQuestion: loading question");
        questionView.setText(actualQuestion.getQuestion());

        boolean[] occupiedButtons = new boolean[4];


        Random rand = new Random();

        int btnNum;
        for (int i = 0; i < 4; i++) {

            btnNum = rand.nextInt(4);

            if (occupiedButtons[btnNum] == false) {
                buttons[btnNum].setText(actualQuestion.getAnswer(i));
                occupiedButtons[btnNum] = true;
            } else
                i--;
        }

        if (actualQuestion.getType() == Question.QuestionType.IMAGE) {

            loadQuestionImage();
        }
        else {
            //Text type
            //imageView.setImageResource(R.drawable.no_image);
            imageView.setVisibility(View.INVISIBLE);

        }
    }

    /**
     *
     */
    private void loadQuestionImage() {

        imageView.setImageResource(this.getResources().getIdentifier(actualQuestion.getResource(),"drawable",this.getPackageName()));
        imageView.setVisibility(View.VISIBLE);

    }

    private void answerSelection(int btnIndex) {
        String answer = buttons[btnIndex].getText().toString();
        boolean correct;
        if (correct = answer.equals(actualQuestion.getCorrectAns())) {
            //Correct response
            Log.d(this.getClass().getSimpleName(), "answerSelection: Correct answer");
            gameState.addCorrectAnswer();
            playSound(correctSound);
            showAnswerDialog(correct);

        } else {
            //Wrong response
            Log.d(this.getClass().getSimpleName(), "answerSelection: Wrong answer");

            //TODO display a dialog and with the option of quit game and see correct answer
            //TODO or continue till the end and see the correct answers then
            gameState.addWrongAnswer();

            playSound(wrongSound);
            showAnswerDialog(correct);


        }



        //TODO Save the results
    }

    /**
     * @param correct
     * @see <a href="https://developer.android.com/guide/topics/ui/dialogs.html">Reference</a>
     */
    private void showAnswerDialog(boolean correct) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (correct) {
            builder.setTitle(R.string.correct_dialog_title)
                    .setMessage(R.string.correct_dialog_message)
                    .setPositiveButton(R.string.correct_dialog_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            soundPool.autoPause();

                            if (gameState.hasGameFinnished()) {
                                Log.d(this.getClass().getSimpleName(), "Game finished");
                                printResults();

                            } else {
                                loadQuestion();
                            }
                        }
                    })
                    .setCancelable(false)
                    .create()
                    .show();

        } else {
            builder.setTitle(R.string.wrong_dialog_title)
                    .setMessage(R.string.wrong_dialog_message)
                    .setPositiveButton(R.string.wrong_dialog_positive_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            soundPool.autoPause();

                            if (gameState.hasGameFinnished()) {
                                Log.d(this.getClass().getSimpleName(), "Game finished");
                                printResults();


                            } else {
                                loadQuestion();
                            }
                        }
                    })
                    .setNegativeButton(R.string.wrong_dialog_negative_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            soundPool.autoPause();

                            printResults();
                        }
                    })
                    .setCancelable(false)
                    .create()
                    .show();
        }

    }

    private void printResults() {

        Results.addNewGame(gameState);

        /*
        StringBuilder sb = new StringBuilder();

        double percent = gameState.getCorrectPercent();

        sb.append(getString(R.string.results_dialog_correct_questions) +"\t\t"+ gameState.getCorrectAnswers()+"\n");
        sb.append(getString(R.string.results_dialog_wrong_questions) +"\t\t\t"+ gameState.getWrongAnswers()+"\n");
        sb.append(getString(R.string.results_dialog_percent_questions) +"\t\t"+ String.format("%.2f", percent)
                + getString(R.string.percent)+"\n\n");
        if (percent > 50){
            sb.append(getString(R.string.results_dialog_good_result));
        }
        else{
            sb.append(getString(R.string.results_dialog_bad_result));
        }
*/

        LayoutInflater inflater = ((LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE));
        View finalDialog = inflater.inflate(R.layout.end_game_dialog, null);

        TextView correctTV = (TextView)finalDialog.findViewById(R.id.preguntas_acertadas_questions);
        TextView wrongTV = (TextView)finalDialog.findViewById(R.id.preguntas_falladas_questions);
        TextView percentTV = (TextView)finalDialog.findViewById(R.id.porcentaje_questions);
        TextView finalMessage = (TextView)finalDialog.findViewById(R.id.final_message_questions);


        correctTV.setText(this.getString(R.string.results_dialog_correct_questions) +"\t\t"+ gameState.getCorrectAnswers());
        wrongTV.setText(this.getString(R.string.results_dialog_wrong_questions) +"\t\t\t"+ gameState.getWrongAnswers());

        double percent = gameState.getCorrectPercent();
        percentTV.setText(this.getString(R.string.results_dialog_percent_questions) +"\t\t"+ String.format("%.2f", percent)
                + this.getString(R.string.percent));

        if (percent > 50){
            finalMessage.setText(this.getString(R.string.results_dialog_good_result));
        }
        else{
            finalMessage.setText(this.getString(R.string.results_dialog_bad_result));

        }


        QuestionsAdapter questionsAdapter = new QuestionsAdapter(this, gameState);
        ListView listView = (ListView) finalDialog.findViewById(R.id.question_list);
        listView.setAdapter(questionsAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.results_dialog_title)
                .setView(finalDialog)
                .setPositiveButton(R.string.results_dialog_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(GameActivity.this, MainActivity.class));
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }
}
