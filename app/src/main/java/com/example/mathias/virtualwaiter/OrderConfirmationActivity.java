package com.example.mathias.virtualwaiter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderConfirmationActivity extends AppCompatActivity {
    private ArrayList<MenuItem> orderList;
    private ArrayList<MenuItem> orgOrderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        //http://stackoverflow.com/questions/12503836/how-to-save-custom-arraylist-on-android-screen-rotate
        if(savedInstanceState == null || !savedInstanceState.containsKey(Constants.ORDER_LIST)) {
            orgOrderList = getIntent().getBundleExtra(Constants.RESTAURANT_MENU).getParcelableArrayList(Constants.ORDER_LIST);
            orderList = new ArrayList<>();
            for(MenuItem temp: orgOrderList){
                if(temp.Chosen){
                    orderList.add(temp);
                }
            }
        }
        else {
            orderList = savedInstanceState.getParcelableArrayList(Constants.ORDER_LIST);
        }
        updateView();
        Button btnConfirm = (Button) findViewById(R.id.buttonBestil);
        Button btnBack = (Button) findViewById(R.id.buttonVisMenu);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(OrderConfirmationActivity.this,MenuOverviewActivity.class);
                backIntent.putExtra("backMenuExtra",orgOrderList);
                startActivity(backIntent);
                finish();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCheckOutClick(view);
            }
        });
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
        outState.putParcelableArrayList(Constants.ORDER_LIST, (ArrayList<? extends Parcelable>) orderList);
    }

    void onCheckOutClick(View view){
        //http://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-on-android
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Er du sikker").setPositiveButton("Ja", dialogClickListener)
                .setNegativeButton("Nej", dialogClickListener).show();
    }

    //http://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-on-android
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    saveOrderToDB();
                    Intent intent = new Intent(OrderConfirmationActivity.this, StartActivity.class);
                    intent.putExtra(Constants.ORDER_PLACED, true);
                    startActivity(intent);
                    finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };



    private void updateView(){
        TextView txt = (TextView) findViewById(R.id.textView);
        txt.setText("At betale: " + getAccPrice() + " kr.");
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

    private void saveOrderToDB(){
        FoodProfileDBHelper dbHelper = new FoodProfileDBHelper(this);
        for(MenuItem item: orderList){
            dbHelper.insertMenuItem(item);
        }
    }

}
