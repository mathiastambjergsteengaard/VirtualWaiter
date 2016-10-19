package com.example.mathias.virtualwaiter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by solveigdoan on 10/10/16.
 */
public class RestaurantListAdapter extends BaseAdapter {

    private Context mContext;
    private List<RestaurantModel> mRestaurantModelList;


    public RestaurantListAdapter(Context mContext, List<RestaurantModel> mRestaurantModelList) {
        this.mContext = mContext;
        this.mRestaurantModelList = mRestaurantModelList;
    }


    @Override
    public int getCount() {
        return mRestaurantModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRestaurantModelList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) mContext
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.custom_restaurant_overview_row, null);

            TextView Name = (TextView) convertView.findViewById(R.id.restaurant_name);
            TextView Distance = (TextView) convertView.findViewById(R.id.restaurant_distance);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                            Intent intent = new Intent(mContext,Loading.class);
                            intent.putExtra("state","menu");
                            intent.putExtra("restaurantId",mRestaurantModelList.get(position).getID());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                }
            });
            //setting text

            Name.setText(mRestaurantModelList.get(position).getName());
            Distance.setText(mRestaurantModelList.get(position).getDistance()+" meter");
            Name.setTextSize(18);
            Distance.setTextSize(18);
            //save restaurantId to tag
            convertView.setTag(mRestaurantModelList.get(position).getID());

        }
        return convertView;
    }
}
