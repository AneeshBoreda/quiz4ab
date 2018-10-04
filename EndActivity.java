package com.example.android.quiz4ab;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {
    private int mUserScore;
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mSharedPrefE;
    private TextView mCurrentScore;
    private String currentUser;
    private Button mClearScores;
    private TextView[] mScores;
    private Scoreboard mScoreboard;
    @Override
    public void onBackPressed()
    {
    }

    private void updateScoreboard()
    {
        boolean empty=false;
        for(int i=0; i<5;i++)
        {
            if(!empty) {
                String name = mScoreboard.getName(i);
                if (name == null) {
                    empty = true;
                    mScores[i].setText((i+1)+". - - - - -");
                    continue;
                }
                int score=mScoreboard.getScore(i);
                //mScoreboard.printScores();
                mScores[i].setText((i+1)+". "+name+" - "+score);
            }
            else
            {
                mScores[i].setText((i+1)+". - - - - -");
            }

        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        mUserScore=getIntent().getIntExtra("FINALSCORE",-1);
        mSharedPref=this.getSharedPreferences("scores",Context.MODE_PRIVATE);
        mSharedPrefE=mSharedPref.edit();
        currentUser=mSharedPref.getString("CURRENTNAME",null);
        mCurrentScore=(TextView) findViewById(R.id.current_score);
        mCurrentScore.setText("Unfortunate, "+currentUser+". Your score is only "+mUserScore);
        mClearScores=(Button) findViewById(R.id.clear_scores);
        mScores=new TextView[5];
        mScores[0]=(TextView)findViewById(R.id.score0);
        mScores[1]=(TextView)findViewById(R.id.score1);
        mScores[2]=(TextView)findViewById(R.id.score2);
        mScores[3]=(TextView)findViewById(R.id.score3);
        mScores[4]=(TextView)findViewById(R.id.score4);
        mScoreboard=new Scoreboard(mSharedPref,mSharedPrefE);
        mScoreboard.addScore(currentUser,mUserScore);
        updateScoreboard();
        mClearScores.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mScoreboard.clearScores();
                updateScoreboard();
            }
        });
    }
}
