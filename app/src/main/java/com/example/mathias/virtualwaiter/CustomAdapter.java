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
        // default -  return super.getView(position, convertView, parent);
        // add the layout
        LayoutInflater myCustomInflater = LayoutInflater.from(getContext());
        View customView = myCustomInflater.inflate(R.layout.custom_row, parent, false);
        // get references.
        String singleFoodItem = getItem(position);
        TextView itemText = (TextView) customView.findViewById(R.id.foodText);
        ImageView addIcon = (ImageView) customView.findViewById(R.id.addButton);
        ImageView removeIcon = (ImageView) customView.findViewById(R.id.removeButton);
        //ImageView timeIcon = (ImageView) customView.findViewById(R.id.timeButton);

        // dynamically update the text from the array
        itemText.setText(singleFoodItem);
        // using the same image every time
        addIcon.setImageResource(R.mipmap.pluss);
        removeIcon.setImageResource(R.mipmap.fjern);
        //timeIcon.setImageResource(R.mipmap.tid);
        // Now we can finally return our custom View or custom item
        return customView;
    }
}
