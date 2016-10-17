package com.example.mathias.virtualwaiter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MenuLoading extends AsyncTask<Object, Integer, String> {
    Context context;
    String restaurantId;
    ArrayList<MenuItem> menuList;
    @Override
    protected String doInBackground(Object... objects) {
        try {
            restaurantId = (String) objects[0];
            context = (Context) objects[1];
            ExternDBStub db = new ExternDBStub();
            String jsonMenuString = db.getMenu(restaurantId);
            return jsonMenuString;
        }
        catch (Exception e){
            Log.d("failed to get menu", e.toString());
            throw e;
        }


    }
    @Override
    protected void onPostExecute(String result) {
        try {
            ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
            JSONObject jsonMenuObj = new JSONObject(result);
            jsonMenuObj = jsonMenuObj.getJSONObject("menu");
            int numberOfItems = jsonMenuObj.toString().split("item").length;

            for (int i = 1; i < numberOfItems-1; i++) {
                menu.add(createMenuItem(jsonMenuObj.getJSONObject("item" + i), restaurantId));
            }
            Intent nextActivityIntent = new Intent(context,MenuOverviewActivity.class);
            nextActivityIntent.putExtra("menus",menu);
            context.startActivity(nextActivityIntent);
        }
        catch(Exception e){
            Log.d("failed to create menu", e.toString());
        }

    }
    private MenuItem createMenuItem(JSONObject obj,String restaurantId)
    {
        MenuItem menuItem = new MenuItem();
        try {
            int id = Integer.parseInt(obj.getString("id"));
            float price = Float.parseFloat(obj.getString("price"));
            String name = obj.getString("name");
            int tag = MenuItemTags.valueOf(obj.getString("tag"));
            menuItem.Id = id;
            menuItem.Name = name;
            menuItem.Tag = tag;
            menuItem.Price = price;
            menuItem.RestaurantID = restaurantId;

        }catch(Exception e)
        {
            Log.d("creating menu item err",e.toString());
        }
        return menuItem;
    }
}
