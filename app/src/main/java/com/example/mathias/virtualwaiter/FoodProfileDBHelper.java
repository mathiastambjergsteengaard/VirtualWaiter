package com.example.mathias.virtualwaiter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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


    private SQLiteDatabase foodProfileDB;

    public FoodProfileDBHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME
                + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PRICE + " FLOAT, " +
                TAG + " TEXT, " +
                NAME + " TEXT" +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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
    public void insert(String name){
        ContentValues values = new ContentValues();
        values.put(NAME,name);
        foodProfileDB.insert(TABLE_NAME,null, values);
    }

    public Cursor getById(int id){
        String query = "SELECT * FROM " + TABLE_NAME;
        return foodProfileDB.rawQuery(query,null);
    }

}
