package com.example.misslecommand;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

/*
 * The Hornets class is for the hornets flying from the top of the screen
 * towards the Cows. It's trajectory is calculated from it's random
 * spawn location. The specific cow it is intended to destroy is randomly chosen
 * in the HornetCtrl.java class.
 *
 * An array of hornets are kept in the HornetCtrl.java class and instantiates
 * new hornets, the number of which is based on what level the player is on.
 *
 */
public class Hornets {

    public boolean status;

    public Cows targetCow;
    Bitmap mBitmap;
    Bitmap mBitmap2;

    public RectF mRect;
    private float width = 160;
    private float height = 90;

    public float speed;
    private float MAX_SPEED = 250;
    public float xVelocity;
    public float yVelocity;
    public double rotateRad;
    public double rotateDeg;

    public float xPosition;
    public float yPosition;
    public float initX;
    public float initY;
    public float finalX;  // The x coordinate of the cow that the hornets are going to fly to
    public float finalY;  // The y coordinate of the cow that the hornets are going to fly to

    public Hornets(int randX, int SCREEN_TOP, Cows cow, int roundLevel, Context context){
        speed = 100; // Initial speed: 100 pixels per second
        // Increase speed depending on what level it is
        for (int i = 1; i < roundLevel; i++){
            if(speed <= MAX_SPEED){
                speed += 15;
            } else {
                break;
            }
        }
        initX = randX;
        initY = SCREEN_TOP;
        xPosition = randX;
        yPosition = SCREEN_TOP;
        targetCow = cow;
        finalX = targetCow.xPosition;
        finalY = targetCow.yPosition - targetCow.height;
        status = true;

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hornet1);
        mBitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.hornet2);

        mRect = new RectF((float)randX - width/2, (float)SCREEN_TOP - height/2, (float)randX+width/2, (float)SCREEN_TOP+height/2);

        // VELOCITY CODE
        float px = finalX - xPosition;
        float py = finalY - yPosition;

        float distance = (float)Math.sqrt(px*px + py*py);
        float scalar = speed/distance;
        yVelocity = scalar*py;
        xVelocity = scalar*px;

        // ROTATION CODE
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
    Bitmap getBitmap2(){
        return mBitmap2;
    }

    public void exploded() {
        status = false;
    }

}
