package com.example.mathias.virtualwaiter;

/**
 * Created by solveigdoan on 04/10/16.
 */
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomAdapter extends ArrayAdapter<String>  {

    private Context c;

    public CustomAdapter(Context context, String[] foods) {
        super(context, R.layout.custom_row ,foods);
        c = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       
        // adding layout
        LayoutInflater myCustomInflater = LayoutInflater.from(getContext());
        View customView = myCustomInflater.inflate(R.layout.custom_row, parent, false);
        // getting references
        String singleFoodItem = getItem(position);
        TextView itemText = (TextView) customView.findViewById(R.id.foodText);
        ImageView addIcon = (ImageView) customView.findViewById(R.id.addButton);
        ImageView removeIcon = (ImageView) customView.findViewById(R.id.removeButton);
        
        // updating text from the array
        itemText.setText(singleFoodItem);
        // same icons in every row
        addIcon.setImageResource(R.mipmap.pluss);
        removeIcon.setImageResource(R.mipmap.fjern);
      
        return customView;
    }
}
