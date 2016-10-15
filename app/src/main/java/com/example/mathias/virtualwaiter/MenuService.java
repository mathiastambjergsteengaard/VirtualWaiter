package com.example.mathias.virtualwaiter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.util.Log;

public class MenuService extends Service {
    //connecting client to service:

    private final IBinder foodListBinder = new RestaurantBinder();
    private static String LOG_TAG = "BoundService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(LOG_TAG, "in onCreate");
    }
    @Override
    public IBinder onBind(Intent intent) {
        // Returning communication channel to service.
        Log.v(LOG_TAG, "in onBind");
        return foodListBinder;
    }
    @Override
    public void onRebind(Intent intent) {
        Log.v(LOG_TAG, "in onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(LOG_TAG, "in onUnbind");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG, "in onDestroy");
    }

    public class RestaurantBinder extends Binder{
        MenuService getService() {

            return MenuService.this;  //returning reference to MenuService to get access to all its methods
        }


    }


}
