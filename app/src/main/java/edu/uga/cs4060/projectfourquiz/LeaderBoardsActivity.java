package edu.uga.cs4060.projectfourquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * This is the leaderboard activity it will display all the quizzes previously taken
 */
public class LeaderBoardsActivity extends AppCompatActivity{

    QuizInstanceData quizInstanceData = null;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);
        textView = (TextView) findViewById(R.id.textView2);

        quizInstanceData = new QuizInstanceData(this);
        quizInstanceData.open();
        List<QuizInstance> quizzes = quizInstanceData.retrieveAllQuizInstances();
        quizInstanceData.close();
        String myResults = "";
        for(int counter = 0; counter <= quizzes.size()-1;counter++){
            String date = quizzes.get(counter).getDate();
            String numCorrect = Integer.toString(quizzes.get(counter).getNumCorrect());
            String numAnswered = Integer.toString(quizzes.get(counter).getNumAnswered());
            if(!numAnswered.equalsIgnoreCase("6"))
                continue;
            myResults += "Quiz on " + date + ":\n    You got " + numCorrect + " of " + numAnswered + ".\n\n";
        }
        textView.setText(myResults);

    }

    /**
     * Override android back button to go to the main activity
     */
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

}
