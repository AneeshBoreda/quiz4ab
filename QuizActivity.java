package com.example.android.quiz4ab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Vibrator;


public class QuizActivity extends AppCompatActivity {
    private Button mHintButton;
    private boolean mHintUsed;
    private Timer mBgTimer;
    private TimerTask mChangeBg;
    private int mCurrentBg=0;
    private int mScore=0;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private TextView mScoreTextView;
    private LinearLayout mLinearLayout;
    private Vibrator mVibrator;
    private int[] mBgArray = new int[]
            {
                    R.drawable.five,
                    R.drawable.four,
                    R.drawable.three,
                    R.drawable.two,
                    R.drawable.one,
                    R.drawable.zero
            };
    private Question[] mQuestionBank = new Question[]
            {
                    new Question(R.string.q1,R.string.h1,false),
                    new Question(R.string.q2,R.string.h2,false),
                    new Question(R.string.q3,R.string.h3,true),
                    new Question(R.string.q4,R.string.h4,false),
                    new Question(R.string.q5,R.string.h5,true),
                    new Question(R.string.q6,R.string.h6,true),
                    new Question(R.string.q7,R.string.h7,false),
                    new Question(R.string.q8,R.string.h8,true),
                    new Question(R.string.q9,R.string.h9,false),
                    new Question(R.string.q10,R.string.h10,false),
                    new Question(R.string.q11,R.string.h11,true)
            };
    private int mCurrentIndex;
    //private LinearLayout layout;
    private void endQuiz()
    {
        mNextButton.setEnabled(false);
        mFalseButton.setEnabled(false);
        mTrueButton.setEnabled(false);
        mHintButton.setEnabled(false);
        mQuestionTextView.setText(R.string.end_text);
    }
    private TimerTask createTimerTask()
    {
        return new TimerTask() {
            @Override
            public void run() {
                if(mCurrentBg<mBgArray.length-1)
                {
                    mCurrentBg++;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLinearLayout.setBackgroundResource(mBgArray[mCurrentBg]);
                            if(mCurrentBg==mBgArray.length-1)
                            {
                                mVibrator.vibrate(500);
                                endQuestion();
                            }
                        }
                    });

                }
//                else
//                {
//
//                }
            }
        };
    }
    private boolean showToast(boolean userAnswer)
    {
        boolean correctAnswer=mQuestionBank[mCurrentIndex].isAnswerTrue();
        if(userAnswer==correctAnswer)
        {
            correctToast();
            correct();
            return true;
        }
        else
        {
            incorrectToast();
            incorrect();
            return false;
        }
    }
    private void incorrectToast()
    {
        Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();

    }
    private void correctToast()
    {
        Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
    }

    private void correct()
    {
        if(!mHintUsed)
        {
            mScore+=100*(5-mCurrentBg);
        }
        else
        {
            mScore+=50;
        }
        mScoreTextView.setText("Your score: "+mScore);
    }
    private void incorrect()
    {

    }
    @Override
    public void onBackPressed()
    {
    }
    public void endQuestion()
    {
        mHintButton.setEnabled(false);
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
        if(!mHintUsed)
        {
            mBgTimer.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mCurrentIndex=getIntent().getIntExtra("QUESTION",-1);
        if(mCurrentIndex==-1)
        {
            mHintUsed=false;
            mCurrentIndex=0;
        }
        else
        {
            mHintUsed=true;
        }
        mScore=getIntent().getIntExtra("SCORE",0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mVibrator=(Vibrator) getSystemService(QuizActivity.this.VIBRATOR_SERVICE);

        mLinearLayout=(LinearLayout) findViewById(R.id.outer_layout);
        mLinearLayout.setBackgroundResource(R.drawable.five);
        mQuestionTextView=(TextView) findViewById(R.id.question_text_view);
        mScoreTextView=(TextView) findViewById(R.id.score_text_view);
        mScoreTextView.setText("Your score: "+mScore);
        int question=mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        mNextButton=(Button) findViewById(R.id.next_button);
        mTrueButton=(Button) findViewById(R.id.true_button);
        mFalseButton=(Button) findViewById(R.id.false_button);
        mHintButton=(Button) findViewById(R.id.hint_button);
        if(mHintUsed)
        {
            mHintButton.setEnabled(false);
        }
        mHintButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mBgTimer.cancel();
                Intent i = new Intent(QuizActivity.this,HintActivity.class);
                i.putExtra("HINT",getText(mQuestionBank[mCurrentIndex].getHintId()));
                i.putExtra("CURRENTQUESTION",mCurrentIndex);
                i.putExtra("CURRENTSCORE",mScore);
                startActivity(i);
            }
        });
        mNextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex+=1;
                if(mCurrentIndex>=mQuestionBank.length)
                {
                    endQuiz();
                    mBgTimer.cancel();
                    mCurrentBg=0;
                    //mLinearLayout.setBackgroundResource(mBgArray[mCurrentBg]);
                    Intent i = new Intent(QuizActivity.this, EndActivity.class);
                    i.putExtra("FINALSCORE",mScore);
                    startActivity(i);
                }
                else
                {
                    if(!mHintUsed) {
                        mBgTimer.cancel();
                    }
                    else
                    {
                        mHintUsed=false;
                    }
                    mHintButton.setEnabled(true);
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                    mCurrentBg=0;
                    mLinearLayout.setBackgroundResource(mBgArray[mCurrentBg]);
                    int question=mQuestionBank[mCurrentIndex].getTextResId();
                    mQuestionTextView.setText(question);
                    mBgTimer=new Timer();
                    mChangeBg=createTimerTask();
                    mBgTimer.schedule(mChangeBg,1000,1000);

                    //mLinearLayout.setBackgroundResource(mBgArray[mCurrentIndex]);
                }

            }
        });
        mTrueButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(true);
                endQuestion();
                //layout.setBackgroundColor(Color.RED);

            }
        });
        mFalseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast( false);
                endQuestion();
                //layout.setBackgroundColor(Color.GREEN);
            }
        });
        if(!mHintUsed){
            mBgTimer=new Timer();
            mChangeBg=createTimerTask();
            mBgTimer.schedule(mChangeBg,1000,1000);

        }


    }

}
