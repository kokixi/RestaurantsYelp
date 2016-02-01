package com.anagrande.rapy;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class FavoriteRestaurants extends AppCompatActivity {

    private ListView lvFavorites;
    private ArrayList<Restaurant> favRestaurants;
    private RestaurantsDBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_favorites_restaurant);

        lvFavorites = (ListView) findViewById(R.id.lvFavorites);

        favRestaurants = new ArrayList<>();

        dbManager = new RestaurantsDBManager(this);
        populateFavRestaurants();

    }

    private void populateFavRestaurants(){
        getRestaurants();
        RestaurantAdapter adapter = new RestaurantAdapter(this, favRestaurants);
        lvFavorites.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_random_restaurant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getRestaurants(){

        Cursor c = dbManager.setCursor();

        if( c.getCount() > 0 ) {
            c.moveToFirst();
            while(!c.isAfterLast()){
                addToRestaurants(c);
                c.moveToNext();
            }
        }
    }

    private void addToRestaurants(Cursor c){
        String categories[] = {c.getString(2)};
        Restaurant rest = new Restaurant( c.getString(1),categories, c.getString(3));
        favRestaurants.add(rest);
    }
}
