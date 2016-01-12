package com.anagrande.rapy;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by sergioalmecijarodriguez on 11/21/15.
 */
public class RandomRestaurantView {

    private View view;
    public void createView(View v) {
        view = v;
    }

    public void presentRestaurant(Restaurant currentRestaurant){
        TextView tvName = (TextView) view.findViewById(R.id.nameRestaurant);
        tvName.setText(currentRestaurant.getName());

        TextView tvCategory = (TextView) view.findViewById(R.id.tvCategory);
        tvCategory.setText(currentRestaurant.categoriesToString());

        ImageView ivRestaurant = (ImageView) view.findViewById(R.id.ivRestaurant);
        ivRestaurant.setImageResource(0);

        Picasso.with(view.getContext()).load(currentRestaurant.getUrlImage()).
                placeholder(R.drawable.abc_item_background_holo_light).into(ivRestaurant);

    }


}
