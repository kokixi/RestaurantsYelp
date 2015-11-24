package com.anagrande.rapy;

import android.view.View;
import android.widget.TextView;

/**
 * Created by sergioalmecijarodriguez on 11/21/15.
 */
public class RandomRestaurantView {

    private View view;
    public void createView(View v) {
        view = v;
    }

    public void presentRestaurant(Restaurant currentRestaurant){
        TextView textView = (TextView) view.findViewById(R.id.nameRestaurant);
        textView.setText(currentRestaurant.getName());
    }


}
