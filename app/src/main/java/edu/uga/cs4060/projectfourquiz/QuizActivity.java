package edu.uga.cs4060.projectfourquiz;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QuizActivity extends AppCompatActivity
        implements QuizQuestionFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
    }

    /**
     * Communicates between the fragments and the Activity
     * TODO Change the Parameters to something useful
     * @param uri
     */
    @Override
    public void onFragmentInteraction(Uri uri){

        }
}
