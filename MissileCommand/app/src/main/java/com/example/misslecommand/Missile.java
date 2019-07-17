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
    

    public int width;
    public int height;
    Missile(int screenX, int screenY) {
        width = screenX / 100;
        height = screenX / 100;
        
        xVelcoity = screenX / 3;
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
