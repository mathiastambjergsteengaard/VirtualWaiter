package com.example.mathias.virtualwaiter;

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
public class ExternDBStub {
    private String MenuDBStubURL = "https://raw.githubusercontent.com/mathiastambjergsteengaard/VirtualWaiter/master/menus";
    private String RestaurantDBStubURL = "https://raw.githubusercontent.com/mathiastambjergsteengaard/VirtualWaiter/master/restaurants";

    private String getJsonString(){
        String JSONString = "";
        try {
            URL url = new URL(MenuDBStubURL);
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
            JSONString = streamToString(input);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return JSONString;
    }

    private String streamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
