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
 * This class is an abstraction representing a quiz instance stored in the database.
 */
public class QuizInstanceData {

    public static final String DEBUG_TAG = "QuizInstanceData";

    private SQLiteDatabase db;
    private SQLiteOpenHelper quizDbHelper;
    private static final String[] allColumns = {
            QuizDBHelper.QUIZHISTORY_COLUMN_ID,
            QuizDBHelper.QUIZHISTORY_COLUMN_DATE,
            QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_ONE,
            QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_TWO,
            QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_THREE,
            QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_FOUR,
            QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_FIVE,
            QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_SIX,
            QuizDBHelper.QUIZHISTORY_COLUMN_NUMBER_CORRECT,
            QuizDBHelper.QUIZHISTORY_COLUMN_NUMBER_ANSWERED
    };

    public QuizInstanceData(Context context ) {
        this.quizDbHelper = QuizDBHelper.getInstance( context );
    }

    // Open the database
    public void open() {
        db = quizDbHelper.getWritableDatabase();
        Log.d( DEBUG_TAG, "QuizInstanceData: db open" );
    }

    // Close the database
    public void close() {
        if( quizDbHelper != null ) {
            quizDbHelper.close();
            Log.d(DEBUG_TAG, "QuizInstanceData: db closed");
        }
    }

    // Retrieve all quiz instances as a List.
    // This is how we restore persistent objects stored as rows in the quiz instance table in the database.
    // For each retrieved row, we create a new QuizInstance (Java object) instance and add it to the list.
    public List<QuizInstance> retrieveAllQuizInstances() {
        ArrayList<QuizInstance> quizzes = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query( QuizDBHelper.TABLE_QUIZHISTORY, allColumns,
                    null, null, null, null, null );
            // collect all quiz instances into a List
            if( cursor.getCount() > 0 ) {
                while( cursor.moveToNext() ) {
                    long id = cursor.getLong( cursor.getColumnIndex( QuizDBHelper.QUIZHISTORY_COLUMN_ID ) );
                    String date = cursor.getString( cursor.getColumnIndex( QuizDBHelper.QUIZHISTORY_COLUMN_DATE ) );
                    long q1 = cursor.getLong( cursor.getColumnIndex( QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_ONE ) );
                    long q2 = cursor.getLong( cursor.getColumnIndex( QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_TWO ) );
                    long q3 = cursor.getLong( cursor.getColumnIndex( QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_THREE ) );
                    long q4 = cursor.getLong( cursor.getColumnIndex( QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_FOUR ) );
                    long q5 = cursor.getLong( cursor.getColumnIndex( QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_FIVE ) );
                    long q6 = cursor.getLong( cursor.getColumnIndex( QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_SIX ) );
                    int numcorrect = cursor.getInt( cursor.getColumnIndex( QuizDBHelper.QUIZHISTORY_COLUMN_NUMBER_CORRECT ) );
                    int numAnswered = cursor.getInt( cursor.getColumnIndex(QuizDBHelper.QUIZHISTORY_COLUMN_NUMBER_ANSWERED ) );
                    QuizInstance quizInstance = new QuizInstance( date, q1, q2, q3, q4, q5, q6, numcorrect, numAnswered );
                    quizInstance.setId( id );
                    quizzes.add( quizInstance );
                    Log.d( DEBUG_TAG, "Retrieved QuizLead: " + quizInstance );
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
        return quizzes;
    }

    /**
     *  Method to return latest quiz as QuizInstance object
     *
     * @return QuizInstance of latest quiz or null if none
     */
    public QuizInstance retrieveLatestQuiz() {
        QuizInstance latestQuiz = null;
        Cursor cursor = null;

        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query(QuizDBHelper.TABLE_QUIZHISTORY, allColumns,
                    null, null, null, null, null);
            // collect all quiz instances into a List
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                long id = cursor.getLong(cursor.getColumnIndex(QuizDBHelper.QUIZHISTORY_COLUMN_ID));
                String date = cursor.getString(cursor.getColumnIndex(QuizDBHelper.QUIZHISTORY_COLUMN_DATE));
                long q1 = cursor.getLong(cursor.getColumnIndex(QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_ONE));
                long q2 = cursor.getLong(cursor.getColumnIndex(QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_TWO));
                long q3 = cursor.getLong(cursor.getColumnIndex(QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_THREE));
                long q4 = cursor.getLong(cursor.getColumnIndex(QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_FOUR));
                long q5 = cursor.getLong(cursor.getColumnIndex(QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_FIVE));
                long q6 = cursor.getLong(cursor.getColumnIndex(QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_SIX));
                int numcorrect = cursor.getInt(cursor.getColumnIndex(QuizDBHelper.QUIZHISTORY_COLUMN_NUMBER_CORRECT));
                int numAnswered = cursor.getInt(cursor.getColumnIndex(QuizDBHelper.QUIZHISTORY_COLUMN_NUMBER_ANSWERED));
                QuizInstance quizInstance = new QuizInstance(date, q1, q2, q3, q4, q5, q6, numcorrect, numAnswered);
                quizInstance.setId(id);
                latestQuiz = quizInstance;
                Log.d(DEBUG_TAG, "Retrieved QuizLead: " + quizInstance);
            }
        } catch (Exception e) {
            Log.d(DEBUG_TAG, "Exception caught: " + e);
        } finally {
            // we should close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return latestQuiz;
    }

    /**
     * This will create a new row in the db instance table and store the values as those in quizInstance object
     *
     * @param quizInstance the values to store in the db
     * @return the quiz instance stored with the correct id
     */
    public QuizInstance storeQuizInstance( QuizInstance quizInstance ) {

        // Prepare the values for all of the necessary columns in the table
        // and set their values to the variables of the QuizInstance argument.
        // This is how we are providing persistence to a QuizInstance (Java object) instance
        // by storing it as a new row in the database table representing quiz instances.
        ContentValues values = new ContentValues();
        values.put( QuizDBHelper.QUIZHISTORY_COLUMN_DATE, quizInstance.getDate());
        values.put( QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_ONE, quizInstance.getQuestion1() );
        values.put( QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_TWO, quizInstance.getQuestion2() );
        values.put( QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_THREE, quizInstance.getQuestion3() );
        values.put( QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_FOUR, quizInstance.getQuestion4() );
        values.put( QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_FIVE, quizInstance.getQuestion5() );
        values.put( QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_SIX, quizInstance.getQuestion6() );
        values.put( QuizDBHelper.QUIZHISTORY_COLUMN_NUMBER_CORRECT, quizInstance.getNumCorrect() );
        values.put( QuizDBHelper.QUIZHISTORY_COLUMN_NUMBER_ANSWERED, quizInstance.getNumAnswered() );

        // Insert the new row into the database table;  the id (primary key) will be
        // automatically generated by the database system
        long id = db.insert( QuizDBHelper.TABLE_QUIZHISTORY, null, values );

        // store the id in the quiz instance, as it is now persistent
        quizInstance.setId( id );

        Log.d( DEBUG_TAG, "Stored new quiz instance with id: " + String.valueOf( quizInstance.getId() ) );

        return quizInstance;
    }

    /**
     * This will update the db instance table and store the values as those in quizInstance object
     *
     * @param quizInstance the values to store in the db at the id of it
     * @return the quiz instance updated
     */
    public QuizInstance updateQuizInstance( QuizInstance quizInstance ) {

        // Prepare the values for all of the necessary columns in the table
        // and set their values to the variables of the QuizInstance argument.
        // This is how we are providing persistence to a QuizInstance (Java object) instance
        // by storing it as a new row in the database table representing quiz instances.
        ContentValues values = new ContentValues();
        values.put( QuizDBHelper.QUIZHISTORY_COLUMN_DATE, quizInstance.getDate());
        values.put( QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_ONE, quizInstance.getQuestion1() );
        values.put( QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_TWO, quizInstance.getQuestion2() );
        values.put( QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_THREE, quizInstance.getQuestion3() );
        values.put( QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_FOUR, quizInstance.getQuestion4() );
        values.put( QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_FIVE, quizInstance.getQuestion5() );
        values.put( QuizDBHelper.QUIZHISTORY_COLUMN_QUESTION_SIX, quizInstance.getQuestion6() );
        values.put( QuizDBHelper.QUIZHISTORY_COLUMN_NUMBER_CORRECT, quizInstance.getNumCorrect() );
        values.put( QuizDBHelper.QUIZHISTORY_COLUMN_NUMBER_ANSWERED, quizInstance.getNumAnswered() );

        //get the id to update
        long id = quizInstance.getId();

        //update the db at the id obtained from quizInstance
        db.update(QuizDBHelper.TABLE_QUIZHISTORY, values, "_id =" +id,null);

        Log.d( DEBUG_TAG, "Stored new quiz instance with id: " + String.valueOf( quizInstance.getId() ) );

        return quizInstance;
    }
}