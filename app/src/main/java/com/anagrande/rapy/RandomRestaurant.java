package com.anagrande.rapy;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;


public class RandomRestaurant extends AppCompatActivity {

    private static final String CONSUMER_KEY = "2IB5I_3zMgtdWUzfmRqbpA";
    private static final String TOKEN = "39PRWiwVpwzmvsunBolLEvsA7NHcUSEj";
    private static final String CONSUMER_SECRET = "4lT1FMcM9ds5DZUUX0RlJSiwAGI";
    private static final String TOKEN_SECRET = "lbgyWftiGqIjPxtaqQPP8vWxiRM";

    private int numberOfRestaurants;

    private Restaurant restaurant;
    private Random rand;
    private RandomRestaurantView randomRestaurantView;
    private EditText etLocation;
    private String location = "San Francisco";

    private GestureDetector gestureDetector;

    private RestaurantsDBManager dbManager;

    private JSONArray jsonArray;
    float initialX = (float) 0.0;
    boolean updateView = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_restaurant);

        etLocation = (EditText) findViewById(R.id.etLocation);
        etLocation.setText(location);
        etLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etLocation.setText("");
            }
        });
        etLocation.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d("sd", String.valueOf(event.getAction()) + String.valueOf(event.getKeyCode()));
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    location = etLocation.getText().toString();
                    fetchRestaurants();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });


        rand = new Random();
        randomRestaurantView = new RandomRestaurantView();
        randomRestaurantView.createView(findViewById(R.id.layoutText));

        gestureDetector = new GestureDetector(this, new SwipeGesture());

        View.OnTouchListener mGestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (gestureDetector.onTouchEvent(event)) {
                    updateView = true;
                    return true;
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(updateView) {
                        if ((event.getX() - initialX) > 0) {
                            //save on favorites
                            dbManager.addRestaurant(restaurant);
                            Toast.makeText(getBaseContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                        }
                        getRandomRestaurant(jsonArray);
                    }
                    updateView = false;

                }
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    initialX = event.getX();
                }

                return false;
            }
        };

        // attach the OnTouchListener to the image view
        this.findViewById(android.R.id.content).getRootView().setOnTouchListener(mGestureListener);

        dbManager = new RestaurantsDBManager(this);

        fetchRestaurants();
    }

    public void fetchRestaurants() {

        DownloadData dd = new DownloadData();
        dd.execute();

    }


    private class DownloadData extends AsyncTask<Void, Void, String>{


        @Override
        protected String doInBackground(Void... params) {
            YelpAPI y = new YelpAPI(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);

            YelpAPI yelpApi = new YelpAPI(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);

            String data = y.searchForBusinessesByLocation("restaurants", location);

            return data;
        }

        @Override
        protected void onPostExecute(String params) {
            try {
                JSONObject jsonObject = new JSONObject(params);

                jsonArray = jsonObject.getJSONArray("businesses");

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
            String name = jsonArray.getJSONObject(number).get("name").toString();
            JSONArray categoriesJsonArray = jsonArray.getJSONObject(number).getJSONArray("categories");
            String[] categories = new String[categoriesJsonArray.length()];

            for(int i = 0; i < categoriesJsonArray.length(); i++) {
                categories[i] = categoriesJsonArray.getJSONArray(i).get(0).toString();
            }

            String image = jsonArray.getJSONObject(number).get("image_url").toString();
            restaurant = new Restaurant(name, categories, image);

            randomRestaurantView.presentRestaurant(restaurant);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void showFavorites(View v){
        Intent favActivity = new Intent(this, FavoriteRestaurants.class);
        startActivity(favActivity);
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
