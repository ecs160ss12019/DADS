package com.example.misslecommand;

import android.graphics.RectF;

public class Missile {

    public RectF mRect;
//    public float xVelocity;
//    public float yVelocity;
    public int xPosition;
    public int yPosition;
    
    public int width = 10;
    public int height = 10;
    public boolean spawned = false;
    public boolean exploding = false;
    
    public Missile(int baseX, int baseY) {
        xPosition = baseX
        yPosition = baseY + 50;
        
        xVelocity = 500;
        yVelocity = 500;
        
        mRect = new RectF(xPosition - width/2, yPosition + height, xPosition + width/2, yPosition);
    
    }
    void update(long fps){
        // Move the missile based upon the
        // horizontal and vertical speed
        // and the current frame rate(fps)

        // Move the top left corner
        mRect.left = mRect.left + (xVelocity / fps);
        mRect.top = mRect.top + (yVelocity / fps);

        // Match up the bottom right corner
        // based on the size of the missile
        mRect.right = mRect.left + width;
        mRect.bottom = mRect.top + height;
    }

    public void kill(){

    }

    public void increaseSpeed(){

    }

    public void draw(){

    }
}
