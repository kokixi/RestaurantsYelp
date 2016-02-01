package com.anagrande.rapy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class RestaurantAdapter extends ArrayAdapter<Restaurant> {


    public RestaurantAdapter(Context context, List<Restaurant> restaurants) {
        super(context, android.R.layout.simple_list_item_1, restaurants);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Restaurant rest = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_favorites, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tvNameRestaurant);
        TextView tvCategory = (TextView) convertView.findViewById(R.id.tvCategory);
        ImageView ivRestaurant = (ImageView) convertView.findViewById(R.id.ivRestaurant);

        tvName.setText(rest.getName());
        tvCategory.setText(rest.categoriesToString());
        ivRestaurant.setImageResource(0);
        Picasso.with(getContext()).load(rest.getUrlImage()).placeholder(R.drawable.abc_item_background_holo_light).into(ivRestaurant);

        return convertView;
    }

}
