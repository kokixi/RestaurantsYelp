package com.anagrande.rapy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.anagrande.rapy.RestaurantsDBContract.RestaurantsEntry;

import java.util.ArrayList;


public class RestaurantsDBManager {

    private SQLiteDatabase db;
    private RestaurantsDBHelper restHelper;

    RestaurantsDBManager(Context context){
        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.anagrande.rapy/databases/FavoriteRestaurants.db", null,
                    SQLiteDatabase.OPEN_READWRITE);
            restHelper = new RestaurantsDBHelper(context);
        } catch (SQLiteException e) {
            restHelper = new RestaurantsDBHelper(context);
            db = restHelper.getWritableDatabase();
        }
    }

    public void addRestaurant(Restaurant r){

        ContentValues values = new ContentValues();
        values.put(RestaurantsEntry.COLUMN_NAME, r.getName());
        values.put(RestaurantsEntry.COLUMN_TYPE, r.categoriesToString());
        values.put(RestaurantsEntry.COLUMN_IMAGE, r.getUrlImage());

        db.insertWithOnConflict(RestaurantsEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

    }

    public Cursor setCursor(){
        db = restHelper.getReadableDatabase();

        String[] projection = {
                RestaurantsEntry._ID,
                RestaurantsEntry.COLUMN_NAME,
                RestaurantsEntry.COLUMN_TYPE,
                RestaurantsEntry.COLUMN_IMAGE,
                RestaurantsEntry.COLUMN_RATING,
        };

        Cursor c = db.query(
                RestaurantsEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        return c;
    }
}
