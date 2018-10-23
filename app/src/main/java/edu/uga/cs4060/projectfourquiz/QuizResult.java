package edu.uga.cs4060.projectfourquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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

    private void setString(String numCorrect, String numQuizQuestions){
        this.numCorrect = numCorrect;
        this.numQuizQuestions = numQuizQuestions;
        resultsString = "You got \n" + numCorrect + "/" + numQuizQuestions +" correct!";
    }
}
