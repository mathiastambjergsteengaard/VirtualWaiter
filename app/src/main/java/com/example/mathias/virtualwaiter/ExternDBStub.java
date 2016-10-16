package com.example.mathias.virtualwaiter;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//Bruges til at hente den falske database i form a json string
public class ExternDBStub {
    private String MenuDBStubURL = "https://raw.githubusercontent.com/mathiastambjergsteengaard/VirtualWaiter/master/menus";
    private String RestaurantDBStubURL = "https://raw.githubusercontent.com/mathiastambjergsteengaard/VirtualWaiter/master/restaurants";

    public String getRestaurants(){
        String JSONString = "";
        try {
            Http http = new Http();
            JSONString = http.get(RestaurantDBStubURL);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return JSONString;
    }
    public String getMenu(String restaurantId){
        String JSONString = "";
        try {
            Http http = new Http();
            JSONString = http.get(MenuDBStubURL + "/" + restaurantId);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return JSONString;
    }
}
