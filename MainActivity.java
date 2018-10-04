package com.example.android.quiz4ab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button mStartButton;
    private EditText mNameText;
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mSharedPrefE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSharedPref=this.getSharedPreferences("scores",Context.MODE_PRIVATE);
        mSharedPrefE=mSharedPref.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartButton=(Button) findViewById(R.id.start_button);
        mNameText=(EditText) findViewById(R.id.name_text);
        mNameText.setHint(R.string.default_name);
        mNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0)
                {
                    mStartButton.setEnabled(false);
                }
                else
                {
                    mStartButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mStartButton.setEnabled(false);

        //Scoreboard mScoreboard=new Scoreboard(mSharedPref,mSharedPrefE);
        //mScoreboard.printScores();
        mStartButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,QuizActivity.class);
                startActivity(i);
                mSharedPrefE.putString("CURRENTNAME",mNameText.getText().toString());
                mSharedPrefE.commit();

            }
        });

    }
}
