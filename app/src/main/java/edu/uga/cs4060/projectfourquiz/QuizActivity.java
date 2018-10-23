package edu.uga.cs4060.projectfourquiz;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class QuizActivity extends AppCompatActivity
        implements QuizQuestionFragment.OnFragmentInteractionListener {

    private String DEBUG_TAG = "QuizActivity";
    private QuizQuestionsData quizQuestionsData = null;
    private List<QuizQuestions> quizQuestionsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Log.d(DEBUG_TAG, "Starting quiz activity / check if we can pull values from the Database");

        //Create an instance of a quiz question
        quizQuestionsData = new QuizQuestionsData(this);

        new RetreieveQuizQuestions().execute();
    }

    /**
     * Communicates between the fragments and the Activity
     * TODO Change the Parameters to something useful
     * @param uri
     */
    @Override
    public void onFragmentInteraction(Uri uri){

        }

        public class RetreieveQuizQuestions extends AsyncTask<Void, Void, List<QuizQuestions>>{

            @Override
            protected List<QuizQuestions> doInBackground(Void... params){
                //Open the db
                quizQuestionsData.open();

                //Where we can access all the questions
                quizQuestionsList = quizQuestionsData.retrieveAllQuizQuestions();

                Log.d(DEBUG_TAG, "QuizActivity: AsyncTask Size of database: " + quizQuestionsList.size());

                return quizQuestionsList;
            }
        }
}
