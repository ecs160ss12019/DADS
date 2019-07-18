package com.example.misslecommand;

import android.graphics.RectF;

public class PowerUp {

    public boolean status;


    public RectF mRect;
    public float xVelocity;
    public float yVelocity = 400;
    private float width = 69;
    private float height = 69;
    public int xPosition;
    public int yPosition;
    public int finalX;  // The x coordinate of where the power up will fly to
    public int finalY;  // The y coordinate of where the power up will fly to

    public PowerUp(int x, int y, int destY){
        xPosition = x;
        yPosition = 0;
        finalX = x;
        finalY = destY;
        status = true;
        mRect = new RectF((float)x, (float)y, (float)x+width, (float)y+height);
    }

    void update(long fps){

        xPosition = xPosition + (int)(xVelocity/fps);
        yPosition = yPosition + (int)(yVelocity/fps);

        // Move the top left corner
        mRect.left = mRect.left + (xVelocity / fps);
        mRect.top = mRect.top + (yVelocity / fps);

        // Match up the bottom right corner
        // based on the size of the missile
        mRect.right = mRect.left + width;
        mRect.bottom = mRect.top + height;
    }

    // This function kills the powerup if it reaches the end of the screen
    public void kill(){
        if (yPosition >= finalY) {
            status = false;
        }

    }

    public void draw(){

    }

}
