package com.anagrande.rapy;

/**
 * Created by sergioalmecijarodriguez on 11/20/15.
 */
public class Restaurant {

    private String name;
    private String[] categories;
    private String urlImage;

    public Restaurant(String name, String[] categories, String image) {
        this.name = name;
        this.categories = categories;
        urlImage = image;

    }

    public String getName() {
        return name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String categoriesToString() {

        String category = "";

        for(int i = 0; i < categories.length; i++) {
            category += categories[i];
            if(i != categories.length - 1)
                category += ", ";
        }

        return category;

    }
}
