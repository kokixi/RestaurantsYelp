package com.anagrande.rapy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;


public class RandomRestaurant extends AppCompatActivity {

    private static final String CONSUMER_KEY = "2IB5I_3zMgtdWUzfmRqbpA";
    private static final String TOKEN = "39PRWiwVpwzmvsunBolLEvsA7NHcUSEj";
    private static final String CONSUMER_SECRET = "4lT1FMcM9ds5DZUUX0RlJSiwAGI";
    private static final String TOKEN_SECRET = "lbgyWftiGqIjPxtaqQPP8vWxiRM";

    private ArrayList<Restaurant> restaurantList;
    private RestaurantAdapter restaurantAdapter;

    private int numberOfRestaurants;

    private Random rand;
    private RandomRestaurantView randomRestaurantView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_restaurant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton up = (FloatingActionButton) findViewById(R.id.up);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        FloatingActionButton down = (FloatingActionButton) findViewById(R.id.down);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchRestaurants();
            }
        });

        rand = new Random();
        randomRestaurantView = new RandomRestaurantView();
        randomRestaurantView.createView(findViewById(R.id.layoutText));
        fetchRestaurants();
    }

    public void fetchRestaurants() {

        //restaurantList = new ArrayList<>();

        //restaurantAdapter = new RestaurantAdapter(this, android.R.layout.simple_list_item_1, restaurantList);

        DownloadData dd = new DownloadData();
        dd.execute();

    }


    private class DownloadData extends AsyncTask<Void, Void, String>{


        @Override
        protected String doInBackground(Void... params) {
            YelpAPI y = new YelpAPI(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);

            YelpAPI yelpApi = new YelpAPI(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);

            String data = y.searchForBusinessesByLocation("restaurants", "San Francisco");

            return data;
        }

        @Override
        protected void onPostExecute(String params) {
            try {
                JSONObject jsonObject = new JSONObject(params);

                JSONArray jsonArray = jsonObject.getJSONArray("businesses");

                numberOfRestaurants = jsonObject.getInt("total");

                getRandomRestaurant(jsonArray);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void getRandomRestaurant(JSONArray jsonArray) {


        int number = rand.nextInt(20);

        try {
            Restaurant restaurant = new Restaurant(jsonArray.getJSONObject(number).get("name").toString());

            randomRestaurantView.presentRestaurant(restaurant);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

}
