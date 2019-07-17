package com.example.misslecommand;

import android.graphics.RectF;

public class Hornets {

    public RectF mRect;
    public float xVelocity;
    public float yVelocity;
    private float hornWidth;
    private float hornHeight;
    public int xPosition;
    public int yPosition;
    public int finalX;  // The x coordinate of the cow that the hornets are going to fly to
    public int finalY;  // The y coordinate of the cow that the hornets are going to fly to

    public Hornets(int x, int y, int width, int height, int fX, int fY){
        xPosition = x;
        yPosition = y;
        hornWidth = width;
        hornHeight = height;
        finalX = fX;
        finalY = fY;
        mRect = new RectF((float)x, (float)y, (float)x+width, (float)y+height);
    }

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

    public void kill(){

    }

    public void increaseSpeed(){

    }

    public void draw(){

    }

}
