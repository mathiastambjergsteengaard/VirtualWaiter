package com.example.mathias.virtualwaiter;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

public class SuggestionService extends Service {
    private boolean started = false;

    private ServiceBinder ServiceBinder = new ServiceBinder();
    FoodProfileDBHelper dbHelper;

    public class ServiceBinder extends Binder {
        public SuggestionService getService() {
            return SuggestionService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Action", "Binding to service");
        return ServiceBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Action", "onStartCommand - weatherService");
        dbHelper = new FoodProfileDBHelper(this);
        if(!started) {
            SuggestionTimerThread bk = new SuggestionTimerThread(getApplicationContext());
            new Thread(bk).start();
            started = true;
        }
        return super.onStartCommand(intent, flags, startId);
    }
    public MenuItem getSuggestionBasedOnRestaurant(int restaurant_id){
        Log.d("Action","getCurrentWeather - WeatherService");
        return dbHelper.getCurrentWeather();
    }
    public int getRestaurantIdBasedOn(){
        Log.d("Action","getPastWeather - WeatherService");
        return dbHelper.getAllWeatherData();
    }
}
