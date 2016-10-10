package com.example.mathias.virtualwaiter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.os.IBinder;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.content.ServiceConnection;


public class MenuOverviewActivity extends AppCompatActivity {
    ListView custom_listView;
    CustomAdapter customAdapter;
    
    private String[] foods = {"Husets brunch", "Husets burger", "Baconburger", "Kyllingburger", "Husets kartofler", "Pommes fritter",
            "Club sandwich", "Bøf sandwich", "Nicoise salat", "Caesar salat", "Croque monsieur", "Rørt Tatar", "Nachos", "Dagens Suppe", "Choko Fondant",
            "Cheesecake","Quiche","Pariserbøf","Poussin","Jomfruhummer","Pasta med trøffel","Snackbræt","Moules Marinière",
            "dagens frugt","Dagens ost","Crème Brûlée", "Crêpes Suzette","Espresso", "Cortado", "Nikoline juice", "Smoothie",
            "Sodavand", "Mineralvand", "Rødvin","Rosévin","Hvidvin","Champagne","Cava", "Murphys Red","Heineken", "Royal Classic"};


    MenuService foodService;
    Boolean isBound = false;
    ListView customListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        customListView = (ListView) findViewById(R.id.custom_listview);
        Button visMenuButton = (Button) findViewById(R.id.buttonVisMenu);
      
        visMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBound) {

                    customAdapter = new CustomAdapter(MenuOverviewActivity.this, foods);
                    customListView.setAdapter(customAdapter);
                }
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MenuService.class);
        bindService(intent, restaurantConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(restaurantConnection);
            isBound = false;
        }
    }
    private ServiceConnection restaurantConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            MenuService.RestaurantBinder binder = (MenuService.RestaurantBinder) service;
            foodService = binder.getService();
            isBound= true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound= false;
        }
    };


}
