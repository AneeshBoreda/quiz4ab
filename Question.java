package com.example.android.quiz4ab;

public class Question {
    private int mTextResId;
    private int mHintId;
    private boolean mAnswerTrue;



    public Question(int textResourceId, int hintId, boolean answerTrue)
    {
        mTextResId=textResourceId;
        mAnswerTrue=answerTrue;
        mHintId=hintId;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResourceId) {
        mTextResId = textResourceId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
    public int getHintId() {
        return mHintId;
    }

    public void setHintId(int hintId) {
        mHintId = hintId;
    }
}
