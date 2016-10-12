package com.example.mathias.virtualwaiter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Menu2OverviewActivity extends AppCompatActivity {

    CustomAdapter customAdapter;

    private String[] restaurant1 = {"Tom Yum Goong", "Tom Kha Kai", "Satay chicken", "Yom Nua", "Thailandske forårsruller", "Larb moo",
            "Goong Tod", "Tom Yom Pla", "Geng Ped (stærk / spicy)", "Geng Ped (stærk / spicy)", "Geng Ped Goong", "Panaeng Gai", "Panaeng Near ",
            "Panaeng Goong", "Kaeng Ka Ree","Mas Sa Man", "Geng Ped Pad", "Pla Preo Wan", "Pla Lad Prik","Pla Chu Chi", "Khao Pad Kai",
            "Khao Pad Ped ( stærk/spicy )","Khao Tom", "Khao Tom Goong","Pad Thai", "Pad Sea Eaw", "Kuw Tev Nam", "Lun chokoladekage",
            "Pandekage m.vaniljeis", "Irish coffee ", "Thailandsk øl", "Sodavand", "Fadøl"};


    MenuService foodService;
    Boolean isBound = false;
    ListView customListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2_overview);

        customListView = (ListView) findViewById(R.id.custom_listview);
        Button visMenuButton = (Button) findViewById(R.id.buttonVismenuRest1);

        visMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBound) {

                    customAdapter = new CustomAdapter(Menu2OverviewActivity.this, restaurant1);
                    customListView.setAdapter(customAdapter);

                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MenuService.class);
        //     startService(intent);
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

