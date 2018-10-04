package com.example.android.quiz4ab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HintActivity extends AppCompatActivity {
    private Button mBackButton;
    private TextView mAnswer;
    private String hint;
    private int mScore;
    private int questionNumber;
    @Override
    public void onBackPressed()
    {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
        hint=getIntent().getStringExtra("HINT");
        questionNumber=getIntent().getIntExtra("CURRENTQUESTION",-1);
        mScore=getIntent().getIntExtra("CURRENTSCORE",0);
        mBackButton=(Button)findViewById(R.id.back_button);
        mAnswer=(TextView)findViewById(R.id.answer_text_view);
        mAnswer.setText(hint);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(HintActivity.this,QuizActivity.class);
                i.putExtra("QUESTION",questionNumber);
                i.putExtra("SCORE",mScore);
                startActivity(i);
            }
        });
    }
}
