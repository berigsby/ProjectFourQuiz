package edu.uga.cs4060.projectfourquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Quiz Results. Displays the immediate quiz results
 */
public class QuizResult extends AppCompatActivity {
    TextView results;
    String numCorrect = "";
    String numQuizQuestions = "";
    String resultsString = "You got \n" + numCorrect + "/" + numQuizQuestions +"correct!";
    QuizInstanceData quizInstanceData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        results = findViewById(R.id.results);

        quizInstanceData = new QuizInstanceData(this);

        quizInstanceData.open();
        QuizInstance quizInstance = quizInstanceData.retrieveLatestQuiz();
        String numCorrectdb = Integer.toString(quizInstance.getNumCorrect());
        String numAnswereddb = Integer.toString(quizInstance.getNumAnswered());
        quizInstanceData.close();
        setString(numCorrectdb, numAnswereddb);

        results.setText(resultsString);
    }

    /**
     * Set the phrase to display to user
     * @param numCorrect
     * @param numQuizQuestions
     */
    private void setString(String numCorrect, String numQuizQuestions){
        this.numCorrect = numCorrect;
        this.numQuizQuestions = numQuizQuestions;
        resultsString = "You got \n" + numCorrect + "/" + numQuizQuestions +" correct!";
    }

    /**
     * Override the back button and go to main activity
     */
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
