package com.example.mathias.virtualwaiter;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StartActivity extends AppCompatActivity {
    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
    }

    protected void click(View view){
        SuggestionTimerThread t = new SuggestionTimerThread(StartActivity.this);
        new Thread(t).start();
    }
    //heavily inspired from http://stackoverflow.com/questions/6645537/how-to-detect-the-swipe-left-or-right-in-android
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x2 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x1 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    Log.d("Start","start");
                    startActivity(new Intent(StartActivity.this, MenuOverviewActivity.class));
                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
