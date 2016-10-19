package com.example.mathias.virtualwaiter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SuggestionTimerThread implements Runnable {
    private int SLEEP_TIME_MS = 10*1000;
    private Context context_;

    SuggestionTimerThread(Context context){
        context_ = context;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Intent broadCastIntent = new Intent();
                broadCastIntent.setAction("com.example.new_suggestion");
                context_.sendBroadcast(broadCastIntent);
                Thread.sleep(SLEEP_TIME_MS);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
