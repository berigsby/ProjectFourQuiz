package edu.uga.cs4060.projectfourquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This is a SQLiteOpenHelper class, which Android uses to create, upgrade, delete an SQLite database
 * in an app.
 *
 * This class is a singleton, following the Singleton Design Pattern.
 * Only one instance of this class will exist.  To make sure, the
 * only constructor is private.
 * Access to the only instance is via the getInstance method.
 */
public class QuizDBHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "QuizDBHelper";

    private static final String DB_NAME = "quiz.db";
    private static final int DB_VERSION = 1;

    // Define all names (strings) for quiz info table and column names.
    public static final String TABLE_QUIZQUESTIONS = "quizQuestions";
    public static final String QUIZQUESTIONS_COLUMN_ID = "_id";
    public static final String QUIZQUESTIONS_COLUMN_STATE = "state";
    public static final String QUIZQUESTIONS_COLUMN_CAPITALCITY = "capitalcity";
    public static final String QUIZQUESTIONS_COLUMN_SECONDCITY = "secondcity";
    public static final String QUIZQUESTIONS_COLUMN_THIRDCITY = "thirdcity";
    public static final String QUIZQUESTIONS_COLUMN_STATEHOOD = "statehood";
    public static final String QUIZQUESTIONS_COLUMN_CAPITALSINCE = "capitalsince";
    public static final String QUIZQUESTIONS_COLUMN_SIZERANK = "sizerank";

    // Define all names (strings) for quiz history table and column names.
    public static final String TABLE_QUIZHISTORY = "QUIZHISTORY";
    public static final String QUIZHISTORY_COLUMN_ID = "_id";
    public static final String QUIZHISTORY_COLUMN_DATE = "date";
    public static final String QUIZHISTORY_COLUMN_QUESTION_ONE = "questionone";
    public static final String QUIZHISTORY_COLUMN_QUESTION_TWO = "questiontwo";
    public static final String QUIZHISTORY_COLUMN_QUESTION_THREE = "questionthree";
    public static final String QUIZHISTORY_COLUMN_QUESTION_FOUR = "questionfour";
    public static final String QUIZHISTORY_COLUMN_QUESTION_FIVE = "questionfive";
    public static final String QUIZHISTORY_COLUMN_QUESTION_SIX = "questionsix";
    public static final String QUIZHISTORY_COLUMN_NUMBER_CORRECT = "numbercorrect";

    // This is a reference to the only instance for the helper.
    private static QuizDBHelper helperInstance;

    // A Create table SQL statement to create a table for the quiz information.
    // Note that _id is an auto increment primary key, i.e. the database will
    // automatically generate unique id values as keys.
    private static final String CREATE_QUIZQUESTIONS =
            "create table " + TABLE_QUIZQUESTIONS + " ("
                    + QUIZQUESTIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUIZQUESTIONS_COLUMN_STATE + " TEXT, "
                    + QUIZQUESTIONS_COLUMN_CAPITALCITY + " TEXT, "
                    + QUIZQUESTIONS_COLUMN_SECONDCITY + " TEXT, "
                    + QUIZQUESTIONS_COLUMN_THIRDCITY + " TEXT, "
                    + QUIZQUESTIONS_COLUMN_STATEHOOD + " INTEGER, "
                    + QUIZQUESTIONS_COLUMN_CAPITALSINCE + " INTEGER, "
                    + QUIZQUESTIONS_COLUMN_SIZERANK + " INTEGER"
                    + ")";

    // A Create table SQL statement to create a table for the quiz history.
    // Note that _id is an auto increment primary key, i.e. the database will
    // automatically generate unique id values as keys.
    private static final String CREATE_QUIZHISTORY =
            "create table " + TABLE_QUIZHISTORY + " ("
                    + QUIZHISTORY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUIZHISTORY_COLUMN_DATE + " TEXT, "
                    + QUIZHISTORY_COLUMN_QUESTION_ONE + " INTEGER, "
                    + QUIZHISTORY_COLUMN_QUESTION_TWO + " INTEGER, "
                    + QUIZHISTORY_COLUMN_QUESTION_THREE + " INTEGER, "
                    + QUIZHISTORY_COLUMN_QUESTION_FOUR + " INTEGER, "
                    + QUIZHISTORY_COLUMN_QUESTION_FIVE + " INTEGER, "
                    + QUIZHISTORY_COLUMN_QUESTION_SIX + " INTEGER, "
                    + QUIZHISTORY_COLUMN_NUMBER_CORRECT + " INTEGER"
                    + ")";

    // Note that the constructor is private!
    // So, it can be called only from
    // this class, in the getInstance method.
    private QuizDBHelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    // Access method to the single instance of the class
    public static synchronized QuizDBHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            Log.d("QuizDBHelper", "HelperInstance is null, recreated");
            helperInstance = new QuizDBHelper( context.getApplicationContext() );
        }else{
            Log.d("QuizDBHelper", "HelperInstance is not null"); //dont think this is right
        }
        return helperInstance;
    }

    // We must override onCreate method, which will be used to create the database if
    // it does not exist yet.
    @Override
    public void onCreate( SQLiteDatabase db ) {
        //TODO REMOVE this is just a test to see if values are created correctly
      //db.execSQL( "drop table if exists " + TABLE_QUIZQUESTIONS );
        //db.execSQL( "drop table if exists " + TABLE_QUIZHISTORY );

        db.execSQL( CREATE_QUIZQUESTIONS );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZQUESTIONS + " created" );

        db.execSQL( CREATE_QUIZHISTORY );
        Log.d( DEBUG_TAG, "Table" + TABLE_QUIZHISTORY + " created" );
    }

    // We should override onUpgrade method, which will be used to upgrade the database if
    // its version (DB_VERSION) has changed.  This will be done automatically by Android
    // if the version will be bumped up, as we modify the database schema.
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "drop table if exists " + TABLE_QUIZQUESTIONS );
        db.execSQL( "drop table if exists " + TABLE_QUIZHISTORY );
        onCreate( db );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZQUESTIONS + " upgraded" );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZHISTORY + " upgraded" );
    }
}
