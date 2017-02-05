/*
 * File: GameActivity.java
 * Project: ESDLA Quiz
 *
 * Author: Aythami Estévez Olivas
 * Email: aythae[at]gmail[dot]com
 * Date: 31-ene-2017
 * Repository: https://github.com/AythaE/ESDLA-Quiz
 * License: GPL-3.0
 */
package es.aythae.esdlaquiz;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Random;

import es.aythae.esdlaquiz.adapters.QuestionsAdapter;
import es.aythae.esdlaquiz.model.Game;
import es.aythae.esdlaquiz.model.Question;
import es.aythae.esdlaquiz.model.Questions;
import es.aythae.esdlaquiz.model.Results;

/**
 * Class that handle the game UI, loading the questions, showing success, fail and results dialogs
 * to the user, it also play sounds when the user answer a question and handle the actual game with
 * an instance of Game class. Just before abandon this activity save this game to Results class
 */
public class GameActivity extends AppCompatActivity {


    /**
     * The 4 options buttons for each question
     */
    private Button[] buttons = new Button[4];
    /**
     * ImageView to show images in questions of this type
     */
    private ImageView imageView;
    /**
     * TextView to show the question
     */
    private TextView questionView;

    /**
     * LinearLayout that contains the media player controls
     */
    private LinearLayout soundButtons;

    /**
     * Button to play a sound in sound questions
     */
    private ImageButton playButton;

    /**
     * Button to pause a sound in sound questions
     */
    private ImageButton pauseButton;

    /**
     * Button to replay a sound in sound questions
     */
    private ImageButton replayButton;

    /**
     * MediaPlayer to play sound in sound-type questions
     */
    private MediaPlayer mediaPlayer;

    /**
     * SoundPool to play correct or wrong song when the user answer a question
     */
    private SoundPool soundPool;
    /**
     * Sound IDs of the correct and the wrong sound
     */
    private int correctSound, wrongSound;

    /**
     * Static field to store the actual question
     */
    private static Question actualQuestion;
    /**
     * Static field to store the actual game
     */
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
        soundButtons = (LinearLayout) findViewById(R.id.music_buttons);
        playButton = (ImageButton) findViewById(R.id.button_play);
        pauseButton = (ImageButton) findViewById(R.id.button_pause);
        replayButton = (ImageButton) findViewById(R.id.button_replay);

        questionView = (TextView) findViewById(R.id.question);

        // Creates the soundpool
        // Reference: http://www.edumobile.org/android/sound-pool-example-in-android-development/
        // Reference: http://stackoverflow.com/a/27552576/6441806
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }

        // Retrieves the game from an intent extra
        gameState = (Game) getIntent().getSerializableExtra("gameState");

        // Shuffle the questions randomly so its order changes if different games
        Questions.shuffle();

        // Load a question
        loadQuestion();

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

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playQuestionSound();
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseQuestionSound();
            }
        });
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replayQuestionSound();
            }
        });
    }


    /**
     * If the user press back show the results dialog
     */
    @Override
    public void onBackPressed() {
        printResults();
    }

    /**
     * SoundPool creator with the new constructor available since API 21
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        Log.d(this.getClass().getSimpleName(), "New soundpool constructor");

        AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();

        soundPool = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(attr).build();
        correctSound = soundPool.load(this, R.raw.correct_sound, 1);
        wrongSound = soundPool.load(this, R.raw.wrong_sound, 1);
    }

    /**
     * SoundPool creator with the old constructor for devices with less than API 21
     */
    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        Log.d(this.getClass().getSimpleName(), "old soundpool constructor");

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        correctSound = soundPool.load(this, R.raw.correct_sound, 1);
        wrongSound = soundPool.load(this, R.raw.wrong_sound, 1);
    }

    /**
     * Play a preloaded sound with the soundpool
     *
     * @param soundId of the sound to play
     * @see <a href="http://www.edumobile.org/android/sound-pool-example-in-android-development/">Reference</a>
     */
    private void playSound(int soundId) {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = actualVolume / maxVolume;

        soundPool.play(soundId, volume, volume, 1, 0, 1f);
    }

    /**
     * Gets the a question from the Questions class and load it on the UI
     */
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

            if (!occupiedButtons[btnNum]) {
                buttons[btnNum].setText(actualQuestion.getAnswer(i));
                occupiedButtons[btnNum] = true;
            } else
                i--;
        }

        if (actualQuestion.getType() == Question.QuestionType.IMAGE) {
            soundButtons.setVisibility(View.GONE);
            loadQuestionImage();
        } else if (actualQuestion.getType() == Question.QuestionType.SOUND) {
            imageView.setVisibility(View.GONE);
            loadQuestionSound();
        } else {
            //Text type
            imageView.setVisibility(View.GONE);
            soundButtons.setVisibility(View.GONE);


        }

    }

    /**
     * Load the sound of a sound-type question by instantiating a MediaPlayer with that song, show
     * the buttons to control the sound playback and start the playback of the sound after a second
     * (due to the needed time to load the UI)
     */
    private void loadQuestionSound() {
        Log.d(this.getClass().getSimpleName(), "loadQuestionSound: loading sound");

        mediaPlayer = MediaPlayer.create(this, this.getResources().getIdentifier(actualQuestion.getResource(), "raw", this.getPackageName()));

        soundButtons.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) { }
                playQuestionSound();

            }
        }).start();
    }


    /**
     * Play the sound loaded in the actual MediaPlayer, this method is invoked when the user clicks
     * the play button
     */
    private void playQuestionSound() {
        if (mediaPlayer != null) {
            if(!mediaPlayer.isPlaying()){
                mediaPlayer.start();
            }
        }
    }

    /**
     * Replay from the beggining the sound loaded in the actual MediaPlayer, this method is invoked
     * when the user clicks the replay button
     */
    private void replayQuestionSound() {
        if (mediaPlayer != null) {
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
                mediaPlayer.seekTo(0);
                mediaPlayer.start();

        }
    }

    /**
     * Pause the sound loaded in the actual MediaPlayer, this method is invoked when the user clicks
     * the Pause button
     */
    private void pauseQuestionSound() {
        if (mediaPlayer != null) {
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }

        }
    }

    /**
     * Clean the mediaPlayer stoping the current sound, releasing it resources and setting it to
     * null
     */
    private void cleanMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * Load the image attached to an Image question
     */
    private void loadQuestionImage() {
        Log.d(this.getClass().getSimpleName(), "loadQuestionImage: loading image");

        imageView.setImageResource(this.getResources().getIdentifier(actualQuestion.getResource(), "drawable", this.getPackageName()));
        imageView.setVisibility(View.VISIBLE);

    }

    /**
     * Method that handle when the user choose an answer and tells if the answer is correct or wrong
     *
     * @param btnIndex index of the button array to retrieve its answer and compare it with the
     *                 correct answer of the actual question
     */
    private void answerSelection(int btnIndex) {
        String answer = buttons[btnIndex].getText().toString();
        boolean correct;


        cleanMediaPlayer();


        if (correct = answer.equals(actualQuestion.getCorrectAns())) {
            //Correct response
            Log.d(this.getClass().getSimpleName(), "answerSelection: Correct answer");
            gameState.addCorrectAnswer();
            playSound(correctSound);
            showAnswerDialog(correct);

        } else {
            //Wrong response
            Log.d(this.getClass().getSimpleName(), "answerSelection: Wrong answer");

            gameState.addWrongAnswer();

            playSound(wrongSound);
            showAnswerDialog(correct);


        }

    }

    /**
     * Show a dialog when a question is answer
     *
     * @param correct a boolean flag, if true show the correctAnswerDialog if false the wrongAnswer
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

                            // When the user click the button stop the sound reproduction, check if
                            // the game has finished, if it is show the resultsDialog, if not load
                            // another question
                            soundPool.autoPause();

                            if (gameState.hasGameFinished()) {
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

                            // When the user click the button stop the sound reproduction, check if
                            // the game has finished, if it is show the resultsDialog, if not load
                            // another question
                            soundPool.autoPause();

                            if (gameState.hasGameFinished()) {
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

                            // When the user click the button stop the sound reproduction and show
                            // the results dialog before going to the MainActivity
                            soundPool.autoPause();

                            printResults();
                        }
                    })
                    .setCancelable(false)
                    .create()
                    .show();
        }

    }

    /**
     * Show the results dialog, on it show the correct and wrong answer number, a correct percent,
     * a farewell message and the correct answers of every question did in the actual game. It also
     * save this current gameState in the Results class
     */
    private void printResults() {


        cleanMediaPlayer();


        Results.addNewGame(gameState);

        LayoutInflater inflater = ((LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE));
        View finalDialog = inflater.inflate(R.layout.end_game_dialog, null);

        TextView correctTV = (TextView) finalDialog.findViewById(R.id.correct_answered_questions);
        TextView wrongTV = (TextView) finalDialog.findViewById(R.id.wrong_answered_questions);
        TextView percentTV = (TextView) finalDialog.findViewById(R.id.percent_questions);
        TextView finalMessage = (TextView) finalDialog.findViewById(R.id.final_message_questions);


        correctTV.setText(this.getString(R.string.results_dialog_correct_questions) + "\t\t" + gameState.getCorrectAnswers());
        wrongTV.setText(this.getString(R.string.results_dialog_wrong_questions) + "\t\t\t" + gameState.getWrongAnswers());

        double percent = gameState.getCorrectPercent();
        percentTV.setText(this.getString(R.string.results_dialog_percent_questions) + "\t\t" + String.format("%.2f", percent)
                + this.getString(R.string.percent));

        if (percent > 50) {
            finalMessage.setText(this.getString(R.string.results_dialog_good_result));
        } else {
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

    /**
     * Clean the soundPool stopping the current sound, releasing it resources and setting it to
     * null
     */
    private void cleanSoundPool() {
        if (soundPool != null) {
            soundPool.stop(correctSound);
            soundPool.stop(wrongSound);

            soundPool.release();
            soundPool = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        cleanMediaPlayer();
        cleanSoundPool();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanMediaPlayer();
        cleanSoundPool();
    }
}
