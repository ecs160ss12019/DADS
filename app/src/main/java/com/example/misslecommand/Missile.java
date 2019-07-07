package com.example.misslecommand;

import android.graphics.RectF;

public class Missile {

    private RectF mRect;
    private float xVelocity;
    private float yVelocity;
    private float missileWidth;
    private float missileHeight;

    void update(long fps){
        // Move the missile based upon the
        // horizontal and vertical speed
        // and the current frame rate(fps)

        // Move the top left corner
        mRect.left = mRect.left + (xVelocity / fps);
        mRect.top = mRect.top + (yVelocity / fps);

        // Match up the bottom right corner
        // based on the size of the missile
        mRect.right = mRect.left + missileWidth;
        mRect.bottom = mRect.top + missileHeight;
    }

    // Return a reference to mRect to PongGame
    RectF getRect(){
        return mRect;
    }

    public void kill(){

    }

    public void increaseSpeed(){

    }

    public boolean status(){

    }

    public void draw(){

    }
}
