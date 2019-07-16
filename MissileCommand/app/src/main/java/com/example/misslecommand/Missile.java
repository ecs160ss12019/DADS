package com.example.misslecommand;

import android.graphics.RectF;

public class Missile {

    public RectF mRect;
    public float xVelocity;
    public float yVelocity;
    private float missileWidth;
    private float missileHeight;
    public int xPosition;
    public int yPosition;

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

    public void kill(){

    }

    public void increaseSpeed(){

    }

    public void draw(){

    }
}
