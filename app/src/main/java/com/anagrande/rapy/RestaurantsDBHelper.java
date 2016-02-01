package com.anagrande.rapy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.anagrande.rapy.RestaurantsDBContract.RestaurantsEntry;

public class RestaurantsDBHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + RestaurantsEntry.TABLE_NAME + " (" +
                    RestaurantsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    RestaurantsEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    RestaurantsEntry.COLUMN_TYPE + TEXT_TYPE + COMMA_SEP +
                    RestaurantsEntry.COLUMN_IMAGE + TEXT_TYPE + COMMA_SEP +
                    RestaurantsEntry.COLUMN_RATING + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + RestaurantsEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FavoriteRestaurants.db";

    public RestaurantsDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
