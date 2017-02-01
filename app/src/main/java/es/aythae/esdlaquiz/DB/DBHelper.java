/*
 * File: DBHelper.java
 * Project: ESDLA Quiz
 *
 * Author: Aythami Estévez Olivas
 * Email: aythae[at]gmail[dot]com
 * Date: 31-ene-2017
 * Repository: https://github.com/AythaE/ESDLA-Quiz
 * License: GPL-3.0
 */

package es.aythae.esdlaquiz.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import es.aythae.esdlaquiz.R;
import es.aythae.esdlaquiz.model.Question;

/**
 * Class that handle the SQLite DB
 *
 * @author aythae
 * @see <a href="http://www.hermosaprogramacion.com/2014/10/android-sqlite-bases-de-datos/">Reference</a>
 */
public class DBHelper extends SQLiteOpenHelper {


    public static final String BD_NAME = "ESDLA-Quiz_Questions.db";
    public static final int BD_ACTUAL_VERSION = 1;
    public static final String TABLE_NAME = "Questions";
    public static final String ID = "id";
    public static final String TYPE = "type";
    public static final String QUESTION = "question";
    public static final String CORRECT = "correct_answer";
    public static final String WRONG1 = "wrong_answer1";
    public static final String WRONG2 = "wrong_answer2";
    public static final String WRONG3 = "wrong_answer3";
    public static final String RESOURCE = "resource_location";


    //TODO change to private fields
    protected Context context;

    public DBHelper(Context context) {
        super(context, BD_NAME, null, BD_ACTUAL_VERSION);
        this.context = context;
        //this.open();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(this.getClass().getSimpleName(), "Creating " + BD_NAME);

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                TYPE + " INTEGER NOT NULL," +
                QUESTION + " TEXT NOT NULL," +
                CORRECT + " TEXT NOT NULL," +
                WRONG1 + " TEXT NOT NULL," +
                WRONG2 + " TEXT NOT NULL," +
                WRONG3 + " TEXT NOT NULL," +
                RESOURCE + " TEXT)");



        populateBD(db);

    }

    private void populateBD(SQLiteDatabase db) {
        Scanner sc = new Scanner(this.context.getResources().openRawResource(R.raw.database));
        ContentValues values = new ContentValues();

        //Skip the first line to avoid .csv headers
        sc.nextLine();

        while (sc.hasNextLine()) {
            String[] line = sc.nextLine().split(",");

            values.put(QUESTION, line[0]);
            values.put(CORRECT, line[1]);
            values.put(WRONG1, line[2]);
            values.put(WRONG2, line[3]);
            values.put(WRONG3, line[4]);
            values.put(TYPE, line[5]);
            values.put(RESOURCE, line[6]);

            db.insert(TABLE_NAME, null, values);
            values.clear();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        Log.d(this.getClass().getSimpleName(), "Upgrading " + BD_NAME);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    /**
     * Retrieves the content of the database
     *
     * @return ArrayList<Questions> with all the questions
     * @see <a href="http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/">Reference</a>
     */
    public ArrayList<Question> getQuestions() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Question> questions = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question q = new Question(Integer.parseInt(c.getString(0)), Integer.parseInt(c.getString(1)),
                        c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7));
                questions.add(q);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return questions;
    }

}
