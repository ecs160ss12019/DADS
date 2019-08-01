package com.example.misslecommand;

import android.content.Context;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.Log;

public class Missile {
    //MediaPlayer fireSound;
    //MediaPlayer explodeSound;

    Sound sound;

    boolean up;
    boolean insane;

    int ptr = 0;
    boolean flag = true;
    long waitTime;
    int RADIUS_INC = 7;

    public RectF mRect;
    public RectF explodeRect;
    public float speed = 1000;
    public float xVelocity;
    public float yVelocity;
    public float xCenter;
    public float yCenter;
    public float xDest;
    public float yDest;

    public int radius = 20;
    public int MAX_EXPL_RADIUS = 100;
    public int MIN_EXPL_RADIUS = 10;

    public int explosionCounter;
    
    public int width = 30;
    public int height = 30;
    public boolean spawned = false;
    public boolean exploding;
    public boolean done;
    
    public Missile(float baseXCenter, float baseYTop, float xTouch, float yTouch, Context context, Sound snd) {
        // Create MediaPlayers for launch sound and explode sound, start fire sound on creation.
        //fireSound = MediaPlayer.create(context, R.raw.fire);
        //fireSound.start();
        //explodeSound = MediaPlayer.create(context, R.raw.explode);
        sound = snd;
        sound.launch();
        // set coordinate variables
        xCenter = baseXCenter;
        yCenter = baseYTop - height/2;
        xDest = xTouch;
        yDest = yTouch;
        explosionCounter = 0;
        mRect = new RectF( xCenter - width/2, yCenter - height/2,  xCenter + width/2, yCenter+ height/2);

        exploding = false;
        done = false;

        float py = yDest - yCenter;
        if (py == 1.0) {
            yDest++;
            py = yDest - yCenter;
        }
        float px = xDest - xCenter;


        float distance = (float)Math.sqrt(px*px + py*py);
        float scalar = speed/distance;

        yVelocity = scalar*(yDest - yCenter);
        xVelocity = scalar*(xDest - xCenter);

        // A bug caused by firing missiles at around the same y coord as top of the base was making
        // me go insane, hence the "insane" boolean which fixes the problem.
        if (Math.abs(yVelocity) < 20) {
            insane = true;
        }
        else {
            insane = false;
        }

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
        if (!exploding) {

            xCenter = xCenter + xVelocity / (float) fps;
            yCenter = yCenter + yVelocity / (float) fps;

            // Move the top left corner
            mRect.left = mRect.left + xVelocity / (float) fps;
            mRect.top = mRect.top + yVelocity / (float) fps;

            // Match up the bottom right corner
            // based on the size of the missile
            mRect.right = mRect.left + width;
            mRect.bottom = mRect.top + height;
        }
        if (insane && Math.abs(xCenter - xDest) < 10) {
            this.explode();
        }
        else if(yCenter <= yDest && up && !insane) {
            this.explode();
        }
        else if (yCenter >= yDest && !up && !insane) {
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
        //int ptr = 0;
        //fireSound.stop();
        if (explodeRect == null) {
            //explodeSound.start();
            sound.explode();
            explodeRect = new RectF(xDest - radius, yDest - radius, xDest + radius,
                    yDest + radius);
        }
        //boolean flag = true;

        //explodePrevTime = System.currentTimeMillis();

        yield:
        while (flag) {
            //Log.d("Missile", "Explosion while loop");
            Log.d("ptr = ","val:" + Float.toString((float)ptr));
            switch(ptr) {
                case 0:
              //      Log.d("Missile.explode()", "case0");

                    radius+=RADIUS_INC;
                    explodeRect.left-=RADIUS_INC;
                    explodeRect.right+=RADIUS_INC;
                    explodeRect.top-=RADIUS_INC;
                    explodeRect.bottom+=RADIUS_INC;
                    ptr = 1;

                    //    Log.d("ptr = ","val:" + Float.toString((float)ptr));

                    break yield;

                case 1:
                  //  Log.d("Missile.explode()", "case1");
                    if (radius < MAX_EXPL_RADIUS) {
                        ptr = 0;
                        break yield;
                    }
                    ptr = 2;
                    break yield;

                case 2:
                    //Log.d("Missile.explode()", "case2");
                    radius-=RADIUS_INC;
                    explodeRect.left+=RADIUS_INC;
                    explodeRect.right-=RADIUS_INC;
                    explodeRect.top+=RADIUS_INC;
                    explodeRect.bottom-=RADIUS_INC;
                    ptr = 3;
                    break yield;

                case 3:
                    Log.d("Missile.explode()", "case3");
                    if (radius > MIN_EXPL_RADIUS){
                        Log.d("Missile.explode()", "case3 rad > MIN");
                        ptr = 2;
                        break yield;
                    }
                    waitTime = System.currentTimeMillis();
                    ptr = 4;
                    break yield;

                case 4:
                    while ( System.currentTimeMillis() - waitTime < 50){
                        break yield;
                    }
                    ptr = 5;


                case 5:
                    done = true;
                    flag = false;
                    break yield;

            }


        }

        /*
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
        }*/

    }

}
