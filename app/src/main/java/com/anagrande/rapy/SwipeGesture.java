package com.anagrande.rapy;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by sergioalmecijarodriguez on 11/26/15.
 */
public class SwipeGesture extends GestureDetector.SimpleOnGestureListener{

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        return Math.abs(distanceX) > 15;
    }
}
