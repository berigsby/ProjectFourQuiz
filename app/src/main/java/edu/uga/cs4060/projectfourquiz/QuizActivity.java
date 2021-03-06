package edu.uga.cs4060.projectfourquiz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * The engine where most of the quiz runs. Everything is done here.
 */
public class QuizActivity extends AppCompatActivity {
    private String DEBUG_TAG = "QuizActivity";
    private QuizQuestionsData quizQuestionsData = null;
    private QuizInstanceData quizInstanceData = null;
    private List<QuizQuestions> selectedQuizQuestions = new ArrayList<QuizQuestions>();
    private List<QuizQuestions> quizQuestionsList;
    private ConstraintLayout questionPanel;
    private RadioButton answer1, answer2, answer3;
    private RadioGroup answers;
    private TextView question;
    private int currentQuestion = 0;
    int rightRadioID = 0;
    private Button next; //The next button

    /**
     * OnCreate Method sets the up the view and prelaods the DB
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        question = findViewById(R.id.qNumTextView);
        next = findViewById(R.id.nextButton);
        next.setOnClickListener(new NextButtonClicked());
        addOnRadioButtonListener();

        questionPanel = findViewById(R.id.quizParent);

        questionPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //toggleSomething();
            }
        });

        questionPanel.setOnTouchListener(new OnSwipeTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public boolean onSwipeLeft() {
                nextButtonClicked();
                //Toast.makeText(getBaseContext(), "left", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //Create an instance of a quiz question
        quizQuestionsData = new QuizQuestionsData(this); //From this we need to select 6 random quiz questions
        quizInstanceData = new QuizInstanceData(this);
        Log.d(DEBUG_TAG, "Starting quiz activity / check if we can pull values from the Database");

        quizInstanceData.open();
        QuizInstance quizInstance = quizInstanceData.retrieveLatestQuiz();
        quizInstanceData.close();
        if(quizInstance != null && quizInstance.getNumAnswered() != 6){
            currentQuestion = quizInstance.getNumAnswered();
            quizQuestionsData.open();
            quizQuestionsList = quizQuestionsData.retrieveAllQuizQuestions();
            quizQuestionsData.close();
            selectedQuizQuestions.add(quizQuestionsList.get((int)quizInstance.getQuestion1()));
            selectedQuizQuestions.add(quizQuestionsList.get((int)quizInstance.getQuestion2()));
            selectedQuizQuestions.add(quizQuestionsList.get((int)quizInstance.getQuestion3()));
            selectedQuizQuestions.add(quizQuestionsList.get((int)quizInstance.getQuestion4()));
            selectedQuizQuestions.add(quizQuestionsList.get((int)quizInstance.getQuestion5()));
            selectedQuizQuestions.add(quizQuestionsList.get((int)quizInstance.getQuestion6()));
            Log.d(DEBUG_TAG, "Quiz resumed");
            Toast.makeText(getBaseContext(), "Quiz Resumed at question "+(currentQuestion+1), Toast.LENGTH_SHORT).show();
            setLayoutForQuiz(selectedQuizQuestions.get(currentQuestion));
        } else {
            new RetreieveQuizQuestions().execute();
        }

        //If the quiz is resumed we need to do something here

        //However this will be for the first quiz
        //selectQuizQuestions(6);
        //setLayoutForQuiz(selectedQuizQuestions.get(0)); //For the first instance
    }

    /**
     * Implements the onClickLisnter for the next button
     */
    class NextButtonClicked implements View.OnClickListener{
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View v){
            nextButtonClicked();
        }
    }

    /**
     * The functionality to the next button
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void nextButtonClicked() {
        //We need to get the selected radio button, if null do not continue
        //Toast please enter your answer
        int userSelection = answers.getCheckedRadioButtonId();
        boolean answerCorrect = false;

        if (userSelection == -1) {
            Toast.makeText(getBaseContext(), "Please enter your answer", Toast.LENGTH_SHORT).show();
        } else {
            //Then we need to check whether or not you are correct
            if(userSelection == rightRadioID){
                //This is the correct answer
                Toast.makeText(getBaseContext(), "Correct!", Toast.LENGTH_SHORT).show();
                answerCorrect = true;

            }else{
                //Wrong answer
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
            }
            quizInstanceData.open();
            QuizInstance quizInstance = quizInstanceData.retrieveLatestQuiz();

            String date = LocalDate.now().toString() + " " + LocalTime.now().toString();
            quizInstance.setDate(date);
            int numCorrect = quizInstance.getNumCorrect();
            if(answerCorrect){
                numCorrect ++;
            }
            int numAns = 1 + quizInstance.getNumAnswered();
            quizInstance.setNumCorrect(numCorrect);
            quizInstance.setNumAnswered(numAns);
            //QuizInstance quizInstance = new QuizInstance(date,q1,q2,q3,q4,q5,q6,numCorrect,numAns);
            quizInstanceData.updateQuizInstance(quizInstance);
            quizInstanceData.close();

            //Then we need to move onto the next question
            currentQuestion++;
            if(currentQuestion < selectedQuizQuestions.size())
            {
                setLayoutForQuiz(selectedQuizQuestions.get(currentQuestion)); //Change 1 to the correct answer
            }else{
                //Show the final screen
                Intent intent = new Intent(this,QuizResult.class);
                startActivity(intent);
            }

        }
        answers.clearCheck();
    }
    /**
     * Takes in the number of quiz questions
     * and sets the list to that many
     * @param num
     */
    private void selectQuizQuestions(int num){

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

        answer1.setOnTouchListener(new OnSwipeTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public boolean onSwipeLeft() {
                nextButtonClicked();
                //Toast.makeText(getBaseContext(), "left", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        answer2.setOnTouchListener(new OnSwipeTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public boolean onSwipeLeft() {
                nextButtonClicked();
                //Toast.makeText(getBaseContext(), "left", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        answer3.setOnTouchListener(new OnSwipeTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public boolean onSwipeLeft() {
                nextButtonClicked();
                //Toast.makeText(getBaseContext(), "left", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    /**
     * Our swipe listener. Will add onto the swipe aka the list of failed tinder dates
     */
    public class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());

        public boolean onTouch(final View v, final MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;


            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                result = onSwipeRight();
                            } else {
                                result = onSwipeLeft();
                            }
                        }
                    } else {
                        if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffY > 0) {
                                result = onSwipeBottom();
                            } else {
                                result = onSwipeTop();
                            }
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public boolean onSwipeRight() {
            return false;
        }

        public boolean onSwipeLeft() {
            return false;
        }

        public boolean onSwipeTop() {
            return false;
        }

        public boolean onSwipeBottom() {
            return false;
        }
    }

    /**
     * Sets the radio group which has changes the value after the user answers a user.
     * @param q
     */
    private void setLayoutForQuiz(QuizQuestions q){
        rightRadioID = 0;
        //We know the right answer is the first value in the capital
        RadioButton[] randomize = new RadioButton[]{
                answer1,
                answer2,
                answer3
        };

        String[] cities = new String[]{q.getCapitalCity(),q.getSecondCity(),q.getThirdCity()};
        HashSet<Integer> noDups = new HashSet<Integer>();

        for(int i = 0; i < cities.length; i++){
            int randomNumber = new Random().nextInt(3);
            if(i == 0){
                noDups.add(randomNumber);
                randomize[randomNumber].setText(cities[0]);
                rightRadioID = randomize[randomNumber].getId();
            }else if(!noDups.contains(randomNumber)){
                noDups.add(randomNumber);
                randomize[randomNumber].setText(cities[i]);
            }else{
                i--; //reiterate
            }
        }

        question.setText("Q"+(currentQuestion+1)+".What is the capital of " + q.getState() + "?");

    }
    /**
     * Communicates between the fragments and the Activity
     * @param uri
     */
    public void onFragmentInteraction(Uri uri){

        }

    /**
     * Obtains the class that extends the AsynTask
     * used to retrieve the values of the DB asynchronously
     */
    public class RetreieveQuizQuestions extends AsyncTask<Void, Void, List<QuizQuestions>>{
        /**
         * Called to instantiate the first quiz
         * @param params
         * @return
         */
            @Override
            protected List<QuizQuestions> doInBackground(Void... params){
                //Open the db
                quizQuestionsData.open();

                //Where we can access all the questions
                quizQuestionsList = quizQuestionsData.retrieveAllQuizQuestions();

                Log.d(DEBUG_TAG, "QuizActivity: AsyncTask Size of database: " + quizQuestionsList.size());

                return quizQuestionsList;
            }

        /**
         *  After the thread is done set the screen and store the quiz into the DB
         * @param q
         */
        @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected void onPostExecute(List<QuizQuestions> q){
                super.onPostExecute(q);
                Log.d(DEBUG_TAG, "QuizActivity: Setting the first question");

                //Starting a new quiz
                selectQuizQuestions(6);
                String date = LocalDate.now().toString() + " " + LocalTime.now().toString();
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
