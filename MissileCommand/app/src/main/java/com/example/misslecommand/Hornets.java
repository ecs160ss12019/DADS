package com.example.misslecommand;

import android.graphics.RectF;

public class Hornets {

    public boolean status;

    public Cows target;

    public RectF mRect;
    public float xVelocity;
    public float yVelocity;
    private float width = 20;
    private float height = 20;
    public int xPosition;
    public int yPosition;
    public int finalX;  // The x coordinate of the cow that the hornets are going to fly to
    public int finalY;  // The y coordinate of the cow that the hornets are going to fly to

    public Hornets(int x, int y, Cows cow, int roundLevel){
        xPosition = x;
        yPosition = y;
        target = cow;
        finalX = target.xPosition;
        finalY = target.yPosition;
        status = true;
        mRect = new RectF((float)x - width/2, (float)y - height/2, (float)x+width/2, (float)y+height/2);

        // VELOCITY CODE
        yVelocity = (finalY - yPosition)/(4 - (roundLevel/3));
        xVelocity = (finalX - xPosition)/(4 - (roundLevel/3));
    }

    void update(long fps){

        xPosition = xPosition + (int)(xVelocity/(fps));
        yPosition = yPosition + (int)(yVelocity/(fps));

        // Move the top left corner
        mRect.left = mRect.left + (xVelocity / fps);
        mRect.top = mRect.top + (yVelocity / fps);

        // Match up the bottom right corner
        // based on the size of the missile
        mRect.right = mRect.left + width;
        mRect.bottom = mRect.top + height;
    }

    public void kill(){
        if (yPosition >= finalY) {
            status = false;
            target.kill();
        }
    }

    public void exploded() {
        status = false;
    }


    public void increaseSpeed(){

    }

    public void draw(){

    }

}
