package com.example.misslecommand;

import android.graphics.RectF;

public class Missile {

    public RectF mRect;
    public RectF explodeRect;
    public float xVelocity;
    public float yVelocity;
    public int xPosition;
    public int yPosition;
    public int xFinal;
    public int yFinal;

    public int radius = 80;

    public int explosionCounter;
    
    public int width = 10;
    public int height = 10;
    public boolean spawned = false;
    public boolean exploding = false;
    public boolean done = false;
    
    public Missile(int baseX, int baseY, int xF, int yF) {
        xPosition = baseX;
        yPosition = baseY + 50;
        xFinal = xF;
        yFinal = yF;
        explosionCounter = 0;
        xVelocity = 500;
        yVelocity = 500;
        
        mRect = new RectF(xPosition - width/2, yPosition + height, xPosition + width/2, yPosition);
    
    }
    void update(long fps){
        // Move the missile based upon the
        // horizontal and vertical speed
        // and the current frame rate(fps)

        //TEMPORARY
        if (exploding) {
            this.explode();
        }
        xPosition = xFinal;
        yPosition = yFinal;
        this.explode();
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

    public void explode() {
        exploding = true;
        if (explodeRect == null) {
            explodeRect = new RectF(xFinal - radius, yFinal + radius, xFinal + radius,
                    yFinal - radius);
        }

        explosionCounter++;

        if((explosionCounter == 30 || explosionCounter == 60)  && explosionCounter < 75){
            explodeRect.left = xFinal - radius - 25;
            explodeRect.top = yFinal + radius + 25;
            explodeRect.right = xFinal + radius + 25;
            explodeRect.bottom = yFinal - radius - 25;
        }

        if((explosionCounter == 100 || explosionCounter == 130) && explosionCounter >= 75){
            explodeRect.left = xFinal - radius + 25;
            explodeRect.top = yFinal + radius - 25;
            explodeRect.right = xFinal + radius - 25;
            explodeRect.bottom = yFinal - radius + 25;
        }

        if (explosionCounter >= 150) {
            done = true;
        }
    }
}
