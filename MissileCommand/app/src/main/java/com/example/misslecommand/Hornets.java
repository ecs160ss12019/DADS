package com.example.misslecommand;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

public class Hornets {

    public boolean status;

    public Cows targetCow;

    public RectF mRect;
    Bitmap mBitmap;
    Bitmap mBitmap2;
    public float speed;
    private float maxSpeed = 250;
    public float xVelocity;
    public float yVelocity;
    private float width = 106;
    private float height = 90;
    public float xPosition;
    public float yPosition;
    public float initX;
    public float initY;
    public float finalX;  // The x coordinate of the cow that the hornets are going to fly to
    public float finalY;  // The y coordinate of the cow that the hornets are going to fly to

    public double rotateRad;
    public double rotateDeg;
    public Hornets(int x, int SCREEN_TOP, Cows cow, int roundLevel, Context context){
        speed = 100;
        for (int i = 1; i < roundLevel; i++){
            if(speed <= maxSpeed){
                speed += 15;
            } else {
                break;
            }
        }
        initX = x;
        initY = SCREEN_TOP;
        xPosition = x;
        yPosition = SCREEN_TOP;
        targetCow = cow;
        finalX = targetCow.xPosition;
        finalY = targetCow.yPosition - targetCow.height;
        status = true;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hornet1);
        mBitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.hornet2);

        mRect = new RectF((float)x - width/2, (float)SCREEN_TOP - height/2, (float)x+width/2, (float)SCREEN_TOP+height/2);


        // VELOCITY CODE
        float px = finalX - xPosition;
        float py = finalY - yPosition;

        double pX = finalX - initX;
        double pY = finalY - initY;
        double slope = pY/pX;

        rotateRad = Math.atan(slope);
        rotateDeg = Math.toDegrees(rotateRad);

        if (rotateDeg > 0) {
            rotateDeg -= 90;
        } else {
            rotateDeg += 90;
        }


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
            targetCow.kill();
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

}
