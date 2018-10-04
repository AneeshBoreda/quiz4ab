package com.example.android.quiz4ab;

import android.content.SharedPreferences;

import java.util.ArrayList;

public class Scoreboard {
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mSharedPrefE;
    private ArrayList<Integer> scores;
    private ArrayList<String> names;
    public Scoreboard(SharedPreferences sP, SharedPreferences.Editor sPE)
    {
        mSharedPref=sP;
        mSharedPrefE=sPE;
        scores=new ArrayList<>();
        names=new ArrayList<>();
        for(int i=1; i<=5; i++)
        {
            String value=mSharedPref.getString(i+"",null);
            if(value==null)
            {
                break;
            }
            String[] info = value.split(" ");
            names.add(info[0]);
            scores.add(Integer.parseInt(info[1]));
        }
        System.out.println(names);
        System.out.println(scores);
    }
    public void clearScores()
    {
        for(int i=1; i<=5; i++)
        {
            mSharedPrefE.remove(i+"");
        }
        mSharedPrefE.commit();
        scores=new ArrayList<>();
        names=new ArrayList<>();
    }
    public void addScore(String name, int score)
    {

        for(int i=0;i<5;i++)
        {
            if(i<scores.size()) {
                if (score > scores.get(i)) {

                    scores.add(i, score);
                    names.add(i, name);
                    if(scores.size()>5) {
                        scores.remove(5);
                        names.remove(5);

                    }
                    break;
                }
            }
            else
            {
                scores.add(i, score);
                names.add(i, name);
                break;
            }
        }
        for(int i = 1; i<=names.size(); i++)
        {
            mSharedPrefE.putString(i+"",getName(i-1)+" "+getScore(i-1));
            System.out.println(getName(i-1)+" "+getScore(i-1));
            System.out.println(names);
            System.out.println(scores);
        }

        mSharedPrefE.commit();
        //mSharedPref.getString(1+"",null);
    }
    public int getScore(int i)
    {
        if(scores.size()>i)
            return scores.get(i);
        return -1;
    }
    public String getName(int i)
    {
        if(scores.size()>i)
            return names.get(i);
        return null;
    }
    public void printScores()
    {
        for(int i = 0; i<5; i++)
        {
            if(getName(i)!=null)
            {
                System.out.println(getName(i)+getScore(i));
            }
            else
            {
                System.out.println(i+" is not set.");
            }
        }
    }
}
