package com.example.mathias.virtualwaiter;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class GooglePlacesRead extends AsyncTask<Object, Integer, String> {
    String googlePlacesData = null;
    double phoneLatitude;
    double phoneLongitude;
    Context context;
    @Override
    protected String doInBackground(Object... inputObj) {
        try {
            phoneLatitude = (double)inputObj[0];
            phoneLongitude = (double)inputObj[1];
            String googlePlacesUrl = (String) inputObj[2];
            context = (Context) inputObj[3];
            Http http = new Http();
            googlePlacesData = http.get(googlePlacesUrl);
        } catch (Exception e) {
            Log.d("Google Place Read", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            ArrayList<RestaurantModel> listOfResturants = new ArrayList<>();
            JSONObject obj = new JSONObject(result);
            JSONArray arr = obj.getJSONArray("results");
            int size = arr.length();
            for (int i = 0;i<size;i++){
                listOfResturants.add(createResturant((JSONObject) arr.get(i)));
            }
            Intent nextActivityIntent = new Intent(context,RestaurantsOverviewActivity.class);
            nextActivityIntent.putExtra("Resturants",  listOfResturants);

            context.startActivity(nextActivityIntent);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //TODO create next activity

    }
    private RestaurantModel createResturant(JSONObject singleJsonObj)
    {
        try {
            RestaurantModel restaurant = new RestaurantModel();
            String latitude = singleJsonObj.getJSONObject("geometry").getJSONObject("location").getString("lat");
            String longitude = singleJsonObj.getJSONObject("geometry").getJSONObject("location").getString("lng");
            Location phone = new Location("phone");
            phone.setLatitude(phoneLatitude);
            phone.setLongitude(phoneLongitude);

            Location mapPlace = new Location("mapPlace");
            mapPlace.setLongitude(Double.valueOf(longitude));
            mapPlace.setLatitude(Double.valueOf(latitude));

            restaurant.setID((String)singleJsonObj.get("id"));
            restaurant.setDistance((int)phone.distanceTo(mapPlace));
            restaurant.setName((String)singleJsonObj.get("name"));

            return restaurant;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}