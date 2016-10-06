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

/**
 * Created by mathias on 06/10/16.
 */
public class SuggestionTimerThread implements Runnable {
    private int SLEEP_TIME_MS = 10*1000;
    private Context context_;

    SuggestionTimerThread(Context context){
        context_ = context;
    }

    public static String streamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    @Override
    public void run() {
        try {
            Log.d("dfsdf","dsfsdfds");
            URL url = new URL("https://github.com/mathiastambjergsteengaard/VirtualWaiter/blob/master/menus");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);

            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            int response;
            conn.connect();
            response = conn.getResponseCode();
            Log.d("responsecode", Integer.toString(response));
            InputStream input = conn.getInputStream();

            Log.d("Stream",streamToString(input));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        /*while(true) {
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
        }*/
    }
}
