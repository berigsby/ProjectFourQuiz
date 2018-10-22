package edu.uga.cs4060.projectfourquiz;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LeaderBoardsActivity extends AppCompatActivity
    implements LeaderBoardsFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);
    }

    /**
     * Interacts with the leaderboard fragment
     * TODO Change the parameters so that we have a good idea
     * @param uri
     */
    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
