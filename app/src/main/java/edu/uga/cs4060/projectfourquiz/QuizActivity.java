package edu.uga.cs4060.projectfourquiz;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity
        implements QuizQuestionFragment.OnFragmentInteractionListener {
        //TODO Remove QuizQuestionFragment.OnFragmentInterationLisnter
    private String DEBUG_TAG = "QuizActivity";
    private QuizQuestionsData quizQuestionsData = null;
    private QuizInstanceData quizInstanceData = null;
    private List<QuizQuestions> selectedQuizQuestions = new ArrayList<QuizQuestions>();
    private List<QuizQuestions> quizQuestionsList;
    private ConstraintLayout questionPanel;
    private RadioButton answer1, answer2, answer3;
    private RadioGroup answers;
    private TextView question;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        question = findViewById(R.id.qNumTextView);
        addOnRadioButtonListener();

        //Set Layout
        questionPanel = findViewById(R.id.questionPanel); //TODO Set gesture later

        //Create an instance of a quiz question
        quizQuestionsData = new QuizQuestionsData(this); //From this we need to select 6 random quiz questions
        quizInstanceData = new QuizInstanceData(this);
        Log.d(DEBUG_TAG, "Starting quiz activity / check if we can pull values from the Database");
        new RetreieveQuizQuestions().execute();


        //If the quiz is resumed we need to do something here

        //However this will be for the first quiz
        //selectQuizQuestions(6);
        //setLayoutForQuiz(selectedQuizQuestions.get(0)); //For the first instance
    }

    /**
     * Takes in the number of quiz questions
     * and sets the list to that many
     * @param num
     */
    private void selectQuizQuestions(int num){
        //List<Integer> noDups = new List<Integer>;
        HashSet<Integer> noDups = new HashSet<Integer>();
        for(int i = 0; i < num; i++){
            int rand = new Random().nextInt(49); //From 0 to 49
            if(noDups.size()==0||noDups.contains(rand)==false)
            {
                selectedQuizQuestions.add(quizQuestionsList.get(rand));
                noDups.add(rand);

            }else{
                Log.d(DEBUG_TAG, "Duplicate accessed choosing another value");
                i--; //Iterate again and choose a non dup
            }
        }
    }

    /**
     * Adds functionality to the radio buttons
     */
    private void addOnRadioButtonListener(){
        //Set radio buttons
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);

        //Radio Group
        answers = findViewById(R.id.radioGroup);


    }

    private void setLayoutForQuiz(QuizQuestions q){
        int rightanswer = 0;
        //We know the right answer is the first value in the capital

        answer1.setText(q.getCapitalCity());
        answer2.setText(q.getSecondCity());
        answer3.setText(q.getThirdCity());

        question.setText("What is the capital of " + q.getState() + "?");

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

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected void onPostExecute(List<QuizQuestions> q){
                super.onPostExecute(q);
                Log.d(DEBUG_TAG, "QuizActivity: Setting the first question");

                //Starting a new quiz
                selectQuizQuestions(6);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String date = (dtf.format(now));
                long q1 = selectedQuizQuestions.get(0).getId();
                long q2 = selectedQuizQuestions.get(1).getId();
                long q3 = selectedQuizQuestions.get(2).getId();
                long q4 = selectedQuizQuestions.get(3).getId();
                long q5 = selectedQuizQuestions.get(4).getId();
                long q6 = selectedQuizQuestions.get(5).getId();
                int numCorrect = 0;
                int numAns = 0;
                QuizInstance quizInstance = new QuizInstance(date,q1,q2,q3,q4,q5,q6,numCorrect,numAns);
                quizInstanceData.open();
                quizInstanceData.storeQuizInstance(quizInstance);
                quizInstanceData.close();
                Log.d(DEBUG_TAG, quizInstance.toString());
                setLayoutForQuiz(selectedQuizQuestions.get(0));

            }
        }
}
