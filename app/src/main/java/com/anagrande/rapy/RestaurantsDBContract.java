package com.anagrande.rapy;

import android.provider.BaseColumns;


public class RestaurantsDBContract {

    public static abstract class RestaurantsEntry implements BaseColumns {
        public static final String TABLE_NAME = "FavRestaurants";
        public static final String _ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_RATING = "rating";
    }

}
