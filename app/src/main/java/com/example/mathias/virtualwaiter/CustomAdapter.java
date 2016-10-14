package com.example.mathias.virtualwaiter;

/**
 * Created by solveigdoan on 04/10/16.
 */
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;


public class CustomAdapter extends BaseAdapter {

    private Context c;
    private List<MenuItem> restaurantMenu;

    public CustomAdapter(Context context, List<MenuItem> menuList) {
        c = context;
        restaurantMenu = menuList;
    }

    @Override
    public int getCount() {
        return restaurantMenu.size();
    }

    @Override
    public MenuItem getItem(int i) {
        return restaurantMenu.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       
        // adding layout
        LayoutInflater myCustomInflater = LayoutInflater.from(c);
        View customView = myCustomInflater.inflate(R.layout.custom_row, parent, false);
        // getting references
        MenuItem singleMenuItem = getItem(position);
        TextView foodText = (TextView) customView.findViewById(R.id.foodText);
        TextView priceText = (TextView) customView.findViewById(R.id.priceText);
        ImageView addIcon = (ImageView) customView.findViewById(R.id.addButton);
        ImageView removeIcon = (ImageView) customView.findViewById(R.id.removeButton);

        if(singleMenuItem.Chosen == true) {
            customView.setBackgroundColor(Color.BLUE);
        }
        // updating text from the array
        foodText.setText(singleMenuItem.Name);
        priceText.setText(Float.toString(singleMenuItem.Price));
        // same icons in every row
        addIcon.setImageResource(R.mipmap.pluss);
        removeIcon.setImageResource(R.mipmap.fjern);

        addIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(restaurantMenu.get(position).Chosen != true) {
                    restaurantMenu.get(position).Chosen = true;
                    notifyDataSetChanged();
                }
            }
        });

        removeIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("Delete", "das");

                if(restaurantMenu.get(position).Chosen != false) {
                    restaurantMenu.get(position).Chosen = false;
                    notifyDataSetChanged();
                }
            }
        });
        return customView;
    }
}
