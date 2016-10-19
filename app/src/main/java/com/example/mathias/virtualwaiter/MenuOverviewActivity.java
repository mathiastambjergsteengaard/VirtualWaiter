package com.example.mathias.virtualwaiter;

import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.os.IBinder;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class MenuOverviewActivity extends AppCompatActivity {
    CustomAdapter customAdapter;
    List<MenuItem> restaurantMenu;


    SuggestionService suggestionService;
    Boolean isBound = false;
    Boolean isBoundSuggestion = false;
    ListView customListView;
    int tag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_overview);
        if(savedInstanceState == null || !savedInstanceState.containsKey(Constants.RESTAURANT_MENU)) {
            Bundle extra = getIntent().getExtras();
            if (extra != null)
            {
                restaurantMenu = (List<MenuItem>)extra.getSerializable("menus");
                if(restaurantMenu == null)
                    restaurantMenu = extra.getParcelableArrayList("backMenuExtra");
            }


        }
        else {
            restaurantMenu = savedInstanceState.getParcelableArrayList(Constants.RESTAURANT_MENU);
        }
        customListView = (ListView) findViewById(R.id.custom_listview);
        customAdapter = new CustomAdapter(MenuOverviewActivity.this, restaurantMenu);
        customListView.setAdapter(customAdapter);
        Button orderConfirmButton = (Button) findViewById(R.id.buttonVisOrdre);
        Button backButton = (Button) findViewById(R.id.buttonMenuBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        orderConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constants.ORDER_LIST, (ArrayList<? extends Parcelable>) restaurantMenu);
                Intent intent = new Intent(MenuOverviewActivity.this, OrderConfirmationActivity.class);
                intent.putExtra(Constants.RESTAURANT_MENU, bundle);
                startActivity(intent);
                finish();

            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.RESTAURANT_MENU, (ArrayList<? extends Parcelable>) restaurantMenu);
    }
    //http://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-on-android
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                  case DialogInterface.BUTTON_POSITIVE:
                    for(MenuItem temp: restaurantMenu){
                        if(temp.Tag == tag){
                            temp.Chosen = true;
                        }
                    }
                    customAdapter.notifyDataSetChanged();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };
    @Override
    protected void onPause() {
        super.onPause();
        if (isBound) {
            unbindService(suggestionConnection);
            isBound = false;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!isBound){
            bindService(new Intent(MenuOverviewActivity.this, SuggestionService.class), suggestionConnection, BIND_AUTO_CREATE);
            isBound = true;
        }
    }
    private ServiceConnection suggestionConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            SuggestionService.ServiceBinder binder = (SuggestionService.ServiceBinder) service;
            Log.d("Before bind", "");
            suggestionService = binder.getService();
            tag = suggestionService.getMostCommonTagById();
            ///http://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-on-android
            MenuItem item = findById(tag);
            if(item != null && item.Chosen == false) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuOverviewActivity.this);
                DecimalFormat df = new DecimalFormat("#.##");
                String message = getResources().getString(R.string.suggestion, item.Name, df.format(item.Price));

                builder.setMessage(message).setPositiveButton("Ja", dialogClickListener)
                        .setNegativeButton("Nej", dialogClickListener).show();
            }
            isBoundSuggestion = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBoundSuggestion = false;
        }
    };


    MenuItem findById(int tag){
        for(MenuItem temp: restaurantMenu){
            if(temp.Tag == tag){
                return temp;
            }
        }
        return null;
    }



}
