package com.example.misslecommand;

// This is the base class. It is responsible for firing missiles at the hornets.

import android.graphics.RectF;

public class Base {

    private boolean status;
    private RectF mRect;
    private float hornWidth;
    private float hornHeight;



    // Return a reference to mRect to PongGame
    RectF getRect(){
        return mRect;
    }

    //will create an instance of missile and then from TouchEvent fire it to a certain location
    public void fire(){
    }

    public void collision(){

    }

    public void draw(){

    }

}
