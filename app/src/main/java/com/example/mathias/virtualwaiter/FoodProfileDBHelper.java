package com.example.mathias.virtualwaiter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

/**
 * Created by mathias on 03/10/16.
 */
public class FoodProfileDBHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "FoodProfileDB.db";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "menu";
    private static final String ID = "_id";
    private static final String PRICE = "price";
    private static final String TAG = "tag";
    private static final String NAME = "name";
    private static final String RESTAURANT_ID = "restaurant_id";


    private SQLiteDatabase foodProfileDB;

    public FoodProfileDBHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String menu_item_query = "CREATE TABLE " + TABLE_NAME
                + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PRICE + " FLOAT, " +
                TAG + " INTEGER, " +
                NAME + " TEXT," +
                RESTAURANT_ID + " INTEGER" +
                ")";
        db.execSQL(menu_item_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void openDB(){
        foodProfileDB = getReadableDatabase();
    }

    public void closeDB(){
        if(foodProfileDB != null && foodProfileDB.isOpen())
        {
            foodProfileDB.close();
        }
    }
    public void insertMenuItem(MenuItem item){
        openDB();
        ContentValues values = new ContentValues();
        values.put(NAME,item.Name);
        values.put(PRICE,item.Price);
        values.put(TAG,item.Tag);
        values.put(RESTAURANT_ID, item.RestaurantID);
        foodProfileDB.insert(TABLE_NAME,null, values);
        closeDB();
    }

    public int getMostCommonTag(int id){
        openDB();
        //http://stackoverflow.com/questions/9853671/sql-select-most-common-values
        String query = "select " + TAG + " from " + TABLE_NAME + " group by " + TAG + " having count(*) = ( select count(*) from "  + TABLE_NAME + " group by " + TAG + " order by count(*) desc limit 1)";
        Cursor cursor = foodProfileDB.rawQuery(query,null);
        cursor.moveToFirst();
        Log.d("Tag", Integer.toString(cursor.getColumnIndex(TAG)));
        closeDB();
        return cursor.getColumnIndex(TAG);
    }

}
