package edu.uga.cs4060.projectfourquiz;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class LeaderBoardsActivity extends AppCompatActivity{

    QuizInstanceData quizInstanceData = null;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        //Calls an async task to update the quizhistory
        new CreateQuizHistoryList().execute(quizInstanceData);
    }

    /**
     * An async task to pull the quiz history from the db and display it
     */
    private class CreateQuizHistoryList extends AsyncTask<QuizInstanceData ,Void, String> {

        @Override
        protected String doInBackground(QuizInstanceData... quizInstanceData1){
            textView = (TextView) findViewById(R.id.textView2);

            quizInstanceData = new QuizInstanceData(getBaseContext());
            //Open the db to read
            quizInstanceData.open();
            //retrieve all the past quizzes
            List<QuizInstance> quizzes = quizInstanceData.retrieveAllQuizInstances();
            quizInstanceData.close();
            String myResults = "";
            for(int counter = 0; counter <= quizzes.size()-1;counter++){
                //Get all the db info we need
                String date = quizzes.get(counter).getDate();
                String numCorrect = Integer.toString(quizzes.get(counter).getNumCorrect());
                String numAnswered = Integer.toString(quizzes.get(counter).getNumAnswered());
                //we want to ignore the rows that are incomplete quizzes (should only ever be the last one)
                if(!numAnswered.equalsIgnoreCase("6"))
                    continue;
                myResults += "Quiz on " + date + ":\n     You got " + numCorrect + " of " + numAnswered + " correct.\n\n";
            }
            textView.setText(myResults);
            return myResults;
        }
    }

}
