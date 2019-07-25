package com.example.misslecommand;

import android.content.Context;
import android.graphics.RectF;
import android.media.MediaPlayer;

public class Missile {
    MediaPlayer fireSound;
    MediaPlayer explodeSound;

    boolean up;

    public RectF mRect;
    public RectF explodeRect;
    public float speed = 1000;
    public float xVelocity;
    public float yVelocity;
    public float xCenter;
    public float yCenter;
    public float xDest;
    public float yDest;

    public int radius = 80;

    public int explosionCounter;
    
    public int width = 30;
    public int height = 30;
    public boolean spawned = false;
    public boolean exploding;
    public boolean done;
    
    public Missile(float baseXCenter, float baseYTop, float xTouch, float yTouch, Context context) {
        // Create MediaPlayers for launch sound and explode sound, start fire sound on creation.
        fireSound = MediaPlayer.create(context, R.raw.fire);
        fireSound.start();
        explodeSound = MediaPlayer.create(context, R.raw.explode);

        // set coordinate variables
        xCenter = baseXCenter;
        yCenter = baseYTop - height/2;
        xDest = xTouch ;
        yDest = yTouch;
        explosionCounter = 0;
        mRect = new RectF( xCenter - width/2, yCenter - height/2,  xCenter + width/2, yCenter+ height/2);

        exploding = false;
        done = false;

        float py = yDest - yCenter;
        float px = xDest - xCenter;

        float distance = (float)Math.sqrt(px*px + py*py);
        float scalar = speed/distance;

        yVelocity = scalar*(yDest - yCenter);
        xVelocity = scalar*(xDest - xCenter);

        if (yDest < baseYTop) {
            up = true;
        }
        else {
            up = false;
        }
    }
    void update(long fps){
        // Move the missile based upon the
        // horizontal and vertical speed
        // and the current frame rate(fps)

        xCenter = xCenter + xVelocity / (float) fps;
        yCenter = yCenter + yVelocity / (float) fps;

        // Move the top left corner
        mRect.left = mRect.left + xVelocity / (float)fps;
        mRect.top = mRect.top + yVelocity / (float)fps;

        // Match up the bottom right corner
        // based on the size of the missile
        mRect.right = mRect.left + width;
        mRect.bottom = mRect.top + height;

        if(yCenter <= yDest && up){
            this.explode();
        }
        else if (yCenter >= yDest && !up) {
            this.explode();
        }
    }

    public void kill(){

    }

    public void increaseSpeed(){

    }

    public void draw(){

    }


    public void explode() {
        exploding = true;
        //fireSound.stop();
        if (explodeRect == null) {
            explodeSound.start();
            explodeRect = new RectF(xDest - radius, yDest + radius, xDest + radius,
                    yDest - radius);
        }

        explosionCounter++;

        if(explosionCounter == 20 || explosionCounter == 40){
            explodeRect.left = xDest - radius - 25;
            explodeRect.top = yDest + radius + 25;
            explodeRect.right = xDest + radius + 25;
            explodeRect.bottom = yDest - radius - 25;
        } else if(explosionCounter == 60 || explosionCounter == 80){
            explodeRect.left = xDest - radius + 25;
            explodeRect.top = yDest + radius - 25;
            explodeRect.right = xDest + radius - 25;
            explodeRect.bottom = yDest - radius + 25;
        } else if (explosionCounter >= 100) {
            done = true;
            if (explodeSound != null & fireSound != null) {
                explodeSound.reset();
                explodeSound.release();
                explodeSound = null;
                fireSound.reset();
                fireSound.release();
                fireSound = null;
            }
        }
    }

}
