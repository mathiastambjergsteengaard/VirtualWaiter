package com.example.mathias.virtualwaiter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by mathias on 06/10/16.
 */
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
                Log.d("hej", "Wait for 10 sec");
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
