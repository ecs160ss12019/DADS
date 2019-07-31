package com.example.misslecommand;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

public class Hornets {

    public boolean status;

    public Cows target;

    public RectF mRect;
    Bitmap mBitmap;
    Bitmap mBitmap2;
    public float speed;
    public float xVelocity;
    public float yVelocity;
    private float width = 90;
    private float height = 90;
    public float xPosition;
    public float yPosition;
    public int initX;
    public int initY;
    public int finalX;  // The x coordinate of the cow that the hornets are going to fly to
    public int finalY;  // The y coordinate of the cow that the hornets are going to fly to

    public Hornets(int x, int y, Cows cow, int roundLevel, Context context){
        speed = 100;
        for (int i = 1; i < roundLevel; i++){
            speed += 15;
        }
        initX = x;
        initY = y;
        xPosition = x;
        yPosition = y;
        target = cow;
        finalX = target.xPosition;
        finalY = target.yPosition;
        status = true;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hornet1);
        mBitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.hornet2);

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

    RectF getRect(){
        return mRect;
    }

    Bitmap getBitmap(){
        return mBitmap;
    }
    Bitmap getBitmap2(){ return mBitmap2; }

    public void exploded() {
        status = false;
    }


    public void increaseSpeed(){

    }

    public void draw(){

    }

}
