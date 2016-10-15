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

import java.util.ArrayList;
import java.util.List;


public class MenuOverviewActivity extends AppCompatActivity {
    ListView custom_listView;
    CustomAdapter customAdapter;
    List<MenuItem> restaurantMenu;

    private String[] restaurant1 = {"Tom Yum Goong", "Tom Kha Kai", "Satay chicken", "Yom Nua", "Thailandske forårsruller", "Larb moo",
            "Goong Tod", "Tom Yom Pla", "Geng Ped (stærk / spicy)", "Geng Ped (stærk / spicy)", "Geng Ped Goong", "Panaeng Gai", "Panaeng Near ",
            "Panaeng Goong", "Kaeng Ka Ree","Mas Sa Man", "Geng Ped Pad", "Pla Preo Wan", "Pla Lad Prik","Pla Chu Chi", "Khao Pad Kai",
            "Khao Pad Ped ( stærk/spicy )","Khao Tom", "Khao Tom Goong","Pad Thai", "Pad Sea Eaw", "Kuw Tev Nam", "Lun chokoladekage",
            "Pandekage m.vaniljeis", "Irish coffee ", "Thailandsk øl", "Sodavand", "Fadøl"};

    private String[] foods = {"Husets brunch", "Husets burger", "Baconburger", "Kyllingburger", "Husets kartofler", "Pommes fritter",
            "Club sandwich", "Bøf sandwich", "Nicoise salat", "Caesar salat", "Croque monsieur", "Rørt Tatar", "Nachos", "Dagens Suppe", "Choko Fondant",
            "Cheesecake","Quiche","Pariserbøf","Poussin","Jomfruhummer","Pasta med trøffel","Snackbræt","Moules Marinière",
            "dagens frugt","Dagens ost","Crème Brûlée", "Crêpes Suzette","Espresso", "Cortado", "Nikoline juice", "Smoothie",
            "Sodavand", "Mineralvand", "Rødvin","Rosévin","Hvidvin","Champagne","Cava", "Murphys Red","Heineken", "Royal Classic"};


    MenuService foodService;
    SuggestionService suggestionService;
    Boolean isBound = false;
    Boolean isBoundSuggestion = false;
    ListView customListView;
    int menu_item_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_overview);
        if(savedInstanceState == null || !savedInstanceState.containsKey(Constants.RESTAURANT_MENU)) {
            restaurantMenu = new ArrayList<MenuItem>();
            fillOrderList();
            bindService(new Intent(MenuOverviewActivity.this, SuggestionService.class), suggestionConnection, BIND_AUTO_CREATE);
        }
        else {
            restaurantMenu = savedInstanceState.getParcelableArrayList(Constants.RESTAURANT_MENU);
        }
        customListView = (ListView) findViewById(R.id.custom_listview);
        Button visMenuButton = (Button) findViewById(R.id.buttonVisMenu);
        customAdapter = new CustomAdapter(MenuOverviewActivity.this, restaurantMenu);
        customListView.setAdapter(customAdapter);
        Log.d("Before bind", "");

    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MenuService.class);
        bindService(intent, restaurantConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.RESTAURANT_MENU, (ArrayList<? extends Parcelable>) restaurantMenu);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(restaurantConnection);
            isBound = false;
        }
    }

    void onShowOrderBtnClick(View view){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.ORDER_LIST, (ArrayList<? extends Parcelable>) restaurantMenu);
        Intent intent = new Intent(MenuOverviewActivity.this, OrderConfirmationActivity.class);
        intent.putExtra(Constants.RESTAURANT_MENU, bundle);
        startActivity(intent);
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

    void onBackBtnClick(View view){
        Intent intent = new Intent(MenuOverviewActivity.this, RestaurantsOverviewActivity.class);
        startActivity(intent);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };


    //http://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-on-android
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                  case DialogInterface.BUTTON_POSITIVE:
                    for(MenuItem temp: restaurantMenu){
                        if(temp.Id == menu_item_id){
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
    private ServiceConnection suggestionConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            SuggestionService.ServiceBinder binder = (SuggestionService.ServiceBinder) service;
            Log.d("Before bind", "");
            suggestionService = binder.getService();
            menu_item_id = suggestionService.getMostCommonTagById(restaurantMenu.get(0).RestaurantID);
            ///http://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-on-android
            MenuItem item = findById(menu_item_id);
            if(item != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuOverviewActivity.this);

                String message = "Baseret på dine tidligere køb vil gerne foreslå at tilføj denne til din ordre: " + item.Name + ". Den koster " + item.Price + " kr. Vil du tilføje denne til din ordre";
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

    MenuItem findById(int id){
        for(MenuItem temp: restaurantMenu){
            if(temp.Id == id){
                return temp;
            }
        }
        return null;
    }

    private void fillOrderList(){
        MenuItem mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        mi.RestaurantID = 2;
        restaurantMenu.add(mi);
        mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        mi.RestaurantID = 2;
        restaurantMenu.add(mi);
        mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        mi.RestaurantID = 2;
        restaurantMenu.add(mi);
        mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        mi.RestaurantID = 2;
        restaurantMenu.add(mi);
        mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        mi.RestaurantID = 2;
        restaurantMenu.add(mi);
        mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        mi.RestaurantID = 2;
        restaurantMenu.add(mi);
        mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        mi.RestaurantID = 2;
        restaurantMenu.add(mi);
        mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        mi.RestaurantID = 2;
        restaurantMenu.add(mi); mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        mi.RestaurantID = 2;
        restaurantMenu.add(mi);
        mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        mi.RestaurantID = 2;
        restaurantMenu.add(mi);
        mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        mi.RestaurantID = 2;
        restaurantMenu.add(mi);
    }


}
