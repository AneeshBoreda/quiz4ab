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
    private Button mViewGlobal;
    private TextView mScores;
    private Scoreboard mScoreboard;
    @Override
    public void onBackPressed()
    {
    }

    private void updateScoreboard()
    {
        boolean empty=false;
        String sbtext="";
        for(int i=0; i<mScoreboard.getMaxScores();i++)
        {
            if(!empty) {
                String name = mScoreboard.getName(i);
                if (name == null) {
                    empty = true;
                    sbtext+=(i+1)+". - - - - -\n";
                    continue;
                }
                int score=mScoreboard.getScore(i);
                //mScoreboard.printScores();
                sbtext+=((i+1)+". "+name+" - "+score+"\n");
            }
            else
            {
                sbtext+=((i+1)+". - - - - -\n");
            }

        }
        mScores.setText(sbtext);

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
        mCurrentScore.setText(currentUser+", your score was "+mUserScore);
        mClearScores=(Button) findViewById(R.id.clear_scores);
        mScores=(TextView)findViewById(R.id.scores);

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
