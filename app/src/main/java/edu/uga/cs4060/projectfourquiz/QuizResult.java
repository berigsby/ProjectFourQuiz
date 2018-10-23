package edu.uga.cs4060.projectfourquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class QuizResult extends AppCompatActivity {
    TextView results;
    String numCorrect = "";
    String numQuizQuestions = "";
    String resultsString = "You got \n" + numCorrect + "/" + numQuizQuestions +"correct!";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        results = findViewById(R.id.results);

        //TODO pull from DB
        setString("Whatever the db values are", "");

        results.setText(resultsString);
    }

    private void setString(String numCorrect, String numQuizQuestions){
        this.numCorrect = numCorrect;
        this.numQuizQuestions = numQuizQuestions;
    }
}
