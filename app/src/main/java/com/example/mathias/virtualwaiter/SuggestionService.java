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
        Log.d("Action", "Binding to Weather service");
        return ServiceBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Action", "onStartCommand - weatherService");
        dbHelper = new FoodProfileDBHelper(this);
        RetrieveDataThread bk = new RetrieveDataThread(getApplicationContext());
        new Thread(bk).start();
        return super.onStartCommand(intent, flags, startId);
    }
    public WeatherInfo getCurrentWeather(){
        Log.d("Action","getCurrentWeather - WeatherService");
        return dbHelper.getCurrentWeather();
    }
    public List<WeatherInfo> getPastWeather(){
        Log.d("Action","getPastWeather - WeatherService");
        return dbHelper.getAllWeatherData();
    }
}
