package com.example.mathias.virtualwaiter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class RestaurantsOverviewActivity extends AppCompatActivity {
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    
     private ListView list_restaurants_overview;
    private RestaurantListAdapter adapter;
    private List <RestaurantModel> RestaurantModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_overview);
        
        list_restaurants_overview = (ListView)findViewById(R.id.list_restaurant_overview);
        RestaurantModelList = new ArrayList<>();
        adapter = new RestaurantListAdapter(getApplicationContext(), RestaurantModelList);
        list_restaurants_overview.setAdapter(adapter);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    Log.d("Back","Back");
                    finish();
                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
