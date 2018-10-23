package edu.uga.cs4060.projectfourquiz;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    private QuizQuestionsData quizQuestionsData = null;
    Button startActivity;
    String DEBUG_TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity = findViewById(R.id.startButton);
        startActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QuizActivity.class);
                startActivity(intent);
            }
        });
        quizQuestionsData = new QuizQuestionsData(this);
        LoadInQuestions();

    }

    private void LoadInQuestions(){
        //Reads in the csv
        //Should only be done the first time the app ever loads
        //Add in if the db exists, dont do this...
        InputStream is = getResources().openRawResource(R.raw.state_capitals);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is,Charset.forName("UTF-8")));
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                //takes in each line and splits at the commas
                String[] tokens = line.split(",");
                //These will be changed to load into the database
                String state = tokens[0];
                if(state.equalsIgnoreCase("State")){
                    continue;
                }
                String capital = tokens[1];
                String city2 = tokens[2];
                String city3 = tokens[3];
                int stathood = Integer.parseInt(tokens[4]);
                int capitalsince = Integer.parseInt(tokens[5]);
                int sizerank = Integer.parseInt(tokens[6]);

                QuizQuestions quizQuestion = new QuizQuestions(state,capital,city2,city3,stathood,capitalsince,sizerank);

                Log.d(DEBUG_TAG,"Trying to add" + quizQuestion.toString());
                //TODO this doesnt seem to be working when the app is first loaded. Works when clicked from a button
                //async task
                new CreateQuizQuestionTask().execute(quizQuestion);
                //break; //TODO remove just to test one instance
            }
        }catch(IOException e){
            Log.e("MainActivity","Couldn't read in csv file");
        }
    }

    private class CreateQuizQuestionTask extends AsyncTask<QuizQuestions, Void, QuizQuestions>{

        @Override
        protected QuizQuestions doInBackground(QuizQuestions... quizQuestion){
            quizQuestionsData.storeQuizQuestion(quizQuestion[0]);
            return quizQuestion[0];
        }

        @Override
        protected void onPostExecute(QuizQuestions quizQuestion){
            Toast.makeText(getApplicationContext(), "Quiz Question created for " + quizQuestion.getState(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        Log.d( "Main Activity", "onResume");
        if(quizQuestionsData != null)
            quizQuestionsData.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        Log.d("Main Activity", "onPause");
        if(quizQuestionsData != null)
            quizQuestionsData.close();
        super.onPause();
    }
}
