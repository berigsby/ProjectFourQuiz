package edu.uga.cs4060.projectfourquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    //TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Testing reading in the csv file
        //textView = (TextView) findViewById(R.id.textView);
        String mytext = "";

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
                mytext += tokens[0] + " ";
                mytext += tokens[1] + " ";
                mytext += tokens[2] + " ";
                mytext += tokens[3] + " ";
                mytext += tokens[4] + " ";
                mytext += tokens[5] + " ";
                mytext += tokens[6] + "\n";
            }
        }catch(IOException e){
            Log.e("MainActivity","Couldn't read in csv file");
        }
        //testing displaying the csv
        //textView.setText(mytext);
    }
}
