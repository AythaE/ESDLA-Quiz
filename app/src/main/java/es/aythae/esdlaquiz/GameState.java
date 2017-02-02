package es.aythae.esdlaquiz;

/**
 * Created by aythae on 2/02/17.
 */

public class GameState {

    public static final int QUESTIONS_PER_GAME = 5;
    private static int correctAnswers;
    private static int wrongAnswers;

    public static void initialize() {
        correctAnswers = 0;
        wrongAnswers = 0;
    }

    public static boolean hasGameFinnished() {
        return (correctAnswers + wrongAnswers) >= QUESTIONS_PER_GAME ? true : false;
    }

    public static int getCorrectAnswers() {
        return correctAnswers;
    }

    public static void setCorrectAnswers(int correctAnswers) {
        GameState.correctAnswers = correctAnswers;
    }

    public static void addCorrectAnswer() {
        GameState.correctAnswers++;
    }

    public static int getWrongAnswers() {
        return wrongAnswers;
    }

    public static void setWrongAnswers(int wrongAnswers) {
        GameState.wrongAnswers = wrongAnswers;
    }

    public static void addWrongAnswer() {
        GameState.wrongAnswers++;
    }

    public static double getCorrectPercent() {
        return ((double)correctAnswers / (correctAnswers + wrongAnswers)) * 100;
    }
}
