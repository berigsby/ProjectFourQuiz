package edu.uga.cs4060.projectfourquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is an abstraction representing quiz questions stored in the database.
 */
public class QuizQuestionsData {

    public static final String DEBUG_TAG = "QuizQuestionsData";

    private SQLiteDatabase db;
    private SQLiteOpenHelper quizDbHelper;
    private static final String[] allColumns = {
            QuizDBHelper.QUIZQUESTIONS_COLUMN_ID,
            QuizDBHelper.QUIZQUESTIONS_COLUMN_STATE,
            QuizDBHelper.QUIZQUESTIONS_COLUMN_CAPITALCITY,
            QuizDBHelper.QUIZQUESTIONS_COLUMN_SECONDCITY,
            QuizDBHelper.QUIZQUESTIONS_COLUMN_THIRDCITY,
            QuizDBHelper.QUIZQUESTIONS_COLUMN_STATEHOOD,
            QuizDBHelper.QUIZQUESTIONS_COLUMN_CAPITALSINCE,
            QuizDBHelper.QUIZQUESTIONS_COLUMN_SIZERANK
    };

    public QuizQuestionsData(Context context ) {
        this.quizDbHelper = QuizDBHelper.getInstance( context );
    }

    // Open the database
    public void open() {
        db = quizDbHelper.getWritableDatabase();
        Log.d( DEBUG_TAG, "QuizQuestionsData: db open" );
    }

    // Close the database
    public void close() {
        if( quizDbHelper != null ) {
            quizDbHelper.close();
            Log.d(DEBUG_TAG, "QuizQuestionsData: db closed");
        }
    }

    // Retrieve all quiz questions as a List.
    // This is how we restore persistent objects stored as rows in the quiz questions table in the database.
    // For each retrieved row, we create a new QuizQuestion (Java object) instance and add it to the list.
    public List<QuizQuestions> retrieveAllQuizQuestions() {
        ArrayList<QuizQuestions> quizQuestions = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query( QuizDBHelper.TABLE_QUIZQUESTIONS, allColumns,
                    null, null, null, null, null );
            // collect all quizquestions into a List
            if( cursor.getCount() > 0 ) {
                while( cursor.moveToNext() ) {
                    long id = cursor.getLong( cursor.getColumnIndex( QuizDBHelper.QUIZQUESTIONS_COLUMN_ID ) );
                    String state = cursor.getString( cursor.getColumnIndex( QuizDBHelper.QUIZQUESTIONS_COLUMN_STATE ) );
                    String capital = cursor.getString( cursor.getColumnIndex( QuizDBHelper.QUIZQUESTIONS_COLUMN_CAPITALCITY ) );
                    String city2 = cursor.getString( cursor.getColumnIndex( QuizDBHelper.QUIZQUESTIONS_COLUMN_SECONDCITY ) );
                    String city3 = cursor.getString( cursor.getColumnIndex( QuizDBHelper.QUIZQUESTIONS_COLUMN_THIRDCITY ) );
                    int statehood = cursor.getInt( cursor.getColumnIndex( QuizDBHelper.QUIZQUESTIONS_COLUMN_STATEHOOD ) );
                    int capitalsince = cursor.getInt( cursor.getColumnIndex( QuizDBHelper.QUIZQUESTIONS_COLUMN_CAPITALSINCE ) );
                    int sizerank = cursor.getInt( cursor.getColumnIndex( QuizDBHelper.QUIZQUESTIONS_COLUMN_SIZERANK ) );
                    QuizQuestions quizQuestion = new QuizQuestions( state,capital,city2,city3,statehood,capitalsince,sizerank );
                    quizQuestion.setId( id );
                    quizQuestions.add( quizQuestion );
                    Log.d( DEBUG_TAG, "Retrieved Quiz Questions: " + quizQuestion );
                }
            }
            Log.d( DEBUG_TAG, "Number of records from DB: " + cursor.getCount() );
        }
        catch( Exception e ){
            Log.d( DEBUG_TAG, "Exception caught: " + e );
        }
        finally{
            // we should close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return quizQuestions;
    }

    // Store a new quiz question in the database
    public QuizQuestions storeQuizQuestion( QuizQuestions quizQuestions ) {

        // Prepare the values for all of the necessary columns in the table
        // and set their values to the variables of the quiz questions argument.
        // This is how we are providing persistence to a QuizQuestions (Java object) instance
        // by storing it as a new row in the database table representing quiz questions.
        ContentValues values = new ContentValues();
        values.put( QuizDBHelper.QUIZQUESTIONS_COLUMN_STATE, quizQuestions.getState());
        values.put( QuizDBHelper.QUIZQUESTIONS_COLUMN_CAPITALCITY, quizQuestions.getCapitalCity() );
        values.put( QuizDBHelper.QUIZQUESTIONS_COLUMN_SECONDCITY, quizQuestions.getSecondCity() );
        values.put( QuizDBHelper.QUIZQUESTIONS_COLUMN_THIRDCITY, quizQuestions.getThirdCity() );
        values.put( QuizDBHelper.QUIZQUESTIONS_COLUMN_STATEHOOD, quizQuestions.getStatehood() );
        values.put( QuizDBHelper.QUIZQUESTIONS_COLUMN_CAPITALSINCE, quizQuestions.getCapitalSince() );
        values.put( QuizDBHelper.QUIZQUESTIONS_COLUMN_SIZERANK, quizQuestions.getSizeRank() );

        // Insert the new row into the database table;  the id (primary key) will be
        // automatically generated by the database system
        long id = db.insert( QuizDBHelper.TABLE_QUIZQUESTIONS, null, values );

        // store the id in the quiz questions instance, as it is now persistent
        quizQuestions.setId( id );

        Log.d( DEBUG_TAG, "Stored new quiz question with id: " + String.valueOf( quizQuestions.getId() ) );

        return quizQuestions;
    }
}