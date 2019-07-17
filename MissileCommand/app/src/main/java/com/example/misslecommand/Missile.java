package com.example.misslecommand;

import android.graphics.RectF;

public class Missile {

    public RectF mRect;
    public float xVelocity;
    public float yVelocity;
    public int xPosition;
    public int yPosition;
    public int width;
    public int height;

    public Missile(int x, int y, int screenX, int screenY) {
        width = screenX / 100;
        height = screenY / 100;
        xPosition = x;
        yPosition = y;
        
        xVelocity = screenX / 3;
        yVelocity = screenX / 3;
        
        mRect = new RectF((float)x - width/2, (float)y + height/2, (float)x + width/2,
                (float)y - height/2);
    
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
