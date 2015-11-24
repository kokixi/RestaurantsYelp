package com.anagrande.rapy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sergioalmecijarodriguez on 11/20/15.
 */
public class RestaurantAdapter extends ArrayAdapter<Restaurant> {


    public RestaurantAdapter(Context context, int resource, List<Restaurant> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Restaurant rest = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_restaurant, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.nameRestaurant);

        //tvName.setText(rest.name);

        return convertView;
    }


}
