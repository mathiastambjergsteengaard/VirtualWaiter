package com.example.mathias.virtualwaiter;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderConfirmationActivity extends AppCompatActivity {
    private List<MenuItem> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int orientation = this.getResources().getConfiguration().orientation;
        if(Configuration.ORIENTATION_PORTRAIT == orientation) {
            setContentView(R.layout.activity_order_confirmation);
        }else{
            setContentView(R.layout.activity_order_confirmation_landscape);
        }
        //http://stackoverflow.com/questions/12503836/how-to-save-custom-arraylist-on-android-screen-rotate
        if(savedInstanceState == null || !savedInstanceState.containsKey("orderlist")) {
            orderList = new ArrayList<MenuItem>();
        }
        else {
            orderList = savedInstanceState.getParcelableArrayList("orderlist");
            Log.d("sdgd", orderList.get(2).Name);
            updateView();
        }
    }

    private String getAccPrice(){
        float acc_price = 0;
        //http://stackoverflow.com/questions/153724/how-to-round-a-number-to-n-decimal-places-in-java
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        for(MenuItem temp:orderList){
            acc_price += temp.Price;
        }
        return df.format(acc_price);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("orderlist", (ArrayList<? extends Parcelable>) orderList);
    }

    void onCheckOutClick(View view){
        fillOrderList();
        updateView();
    }

    private void updateView(){
        TextView txt = (TextView) findViewById(R.id.textView);
        txt.setText("At betale: " + getAccPrice());
        ScrollView sv = (ScrollView) findViewById(R.id.sv);
        LinearLayout ll = new LinearLayout(OrderConfirmationActivity.this);
        ll.setOrientation(LinearLayout.VERTICAL);
        for (MenuItem temp: orderList){
            TextView txtAdd = new TextView(OrderConfirmationActivity.this);
            txtAdd.setText(temp.Name + " - " + temp.Price + " kr.");
            txtAdd.setTextSize(30);
            txtAdd.setGravity(Gravity.CENTER);
            ll.addView(txtAdd);
        }
        sv.addView(ll);
    }

    private void fillOrderList(){
        MenuItem mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        orderList.add(mi);
        mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        orderList.add(mi);
        mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        orderList.add(mi);
        mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        orderList.add(mi);
        mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        orderList.add(mi);
        mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        orderList.add(mi);
        mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        orderList.add(mi);
        mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        orderList.add(mi); mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        orderList.add(mi);
        mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        orderList.add(mi);
        mi = new MenuItem();
        mi.Price = (float)2.34;
        mi.Name = "heehhej";
        orderList.add(mi);
    }
}
