package com.example.misslecommand;

import android.graphics.RectF;

public class Hornets {

    private RectF mRect;
    private float xVelocity;
    private float yVelocity;
    private float hornWidth;
    private float hornHeight;

    void update(long fps){
        // Move the ball based upon the
        // horizontal and vertical speed
        // and the current frame rate(fps)

        // Move the top left corner
        mRect.left = mRect.left + (xVelocity / fps);
        mRect.top = mRect.top + (yVelocity / fps);

        // Match up the bottom right corner
        // based on the size of the missile
        mRect.right = mRect.left + hornWidth;
        mRect.bottom = mRect.top + hornHeight;
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

    public void getPos(){

    }

    public void draw(){

    }

}
