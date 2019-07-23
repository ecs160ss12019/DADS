package com.example.misslecommand;

import android.graphics.RectF;

public class Hornets {

    public boolean status;

    public Cows target;

    public RectF mRect;
    public float speed;
    public float xVelocity;
    public float yVelocity;
    private float width = 20;
    private float height = 20;
    public float xPosition;
    public float yPosition;
    public int initX;
    public int initY;
    public int finalX;  // The x coordinate of the cow that the hornets are going to fly to
    public int finalY;  // The y coordinate of the cow that the hornets are going to fly to

    public Hornets(int x, int y, Cows cow, int roundLevel){
        speed = 200;
        for (int i = 1; i < roundLevel; i++){
            speed += 25;
        }
        initX = x;
        initY = y;
        xPosition = x;
        yPosition = y;
        target = cow;
        finalX = target.xPosition;
        finalY = target.yPosition;
        status = true;
        mRect = new RectF((float)x - width/2, (float)y - height/2, (float)x+width/2, (float)y+height/2);


        // VELOCITY CODE
        float px = finalX - xPosition;
        float py = finalY - yPosition;

        float distance = (float)Math.sqrt(px*px + py*py);
        float scalar = speed/distance;
        yVelocity = scalar*py;
        xVelocity = scalar*px;
    }

    void update(long fps){

        xPosition = xPosition + xVelocity/fps;
        yPosition = yPosition + yVelocity/fps;

        // Move the top left corner
        mRect.left = mRect.left + xVelocity/fps;
        mRect.top = mRect.top + yVelocity/fps;

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
