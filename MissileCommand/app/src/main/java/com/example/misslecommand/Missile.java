package com.example.misslecommand;

import android.graphics.RectF;

/*
 * This is the Missile class. When the player taps the screen, MissileCommand.java calls
 * the Base.java method ```fire()``` instantiates a new Missile where the missiles spawn from, i.e.
 * the top of the base.
 *
 */
public class Missile {
    Sound sound;
    boolean up;
    boolean insane;

    // Variables for missile travelling
    public RectF mRect;
    public int width = 70;
    public int height = 30;
    public float speed = 1000; // Constant speed, 1000 pixels per second.
    public float xVelocity;
    public float yVelocity;
    public float xCenter;
    public float yCenter;
    public float xDest;
    public float yDest;
    public double rotateRad; // Variable used to rotate the missile towards it's destination
    public double rotateDeg;

    // Variables controlling the explosion
    public RectF explodeRect;
    public int radius = 20;
    public int MAX_EXPL_RADIUS = 130;
    public int MIN_EXPL_RADIUS = 10;
    int RADIUS_INC = 5;
    public boolean exploding;
    public boolean doneExploding;
    int sequencePtr = 0;
    long waitTime;
    
    public Missile(float baseXCenter, float baseYTop, float xTouch, float yTouch, Sound snd) {
        // calls the sound.launch() function on creation, because instances off missiles are only created
        // when the player taps, so playing the sound effect as the missile is spawned lines the two
        // up perfectly.
        sound = snd;
        sound.launch();

        // set coordinate variables
        xCenter = baseXCenter;
        yCenter = baseYTop - height/2;
        xDest = xTouch;
        yDest = yTouch;
        mRect = new RectF( xCenter - width/2, yCenter - height/2,  xCenter + width/2, yCenter+ height/2);

        exploding = false;
        doneExploding = false;

        // determine the distance in x and y coordinates from the spawn point to the destination point
        // in order to calculate the velocity.
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

        double pX = xDest - xCenter;
        double pY = yDest - yCenter;
        double slope = pY/pX;

        // use the slope from spawn to destination to calculate the angle that the missile image needs
        // to be rotated so that the missile is facing the direction it is travelling rather than
        // facing straight upwards which looks wrong.
        rotateRad = Math.atan(slope);
        rotateDeg = Math.toDegrees(rotateRad);

        // Need to rotate so the image of the missile aligns
        if (rotateDeg > 0) {
            rotateDeg -= 90;
        } else {
            rotateDeg += 90;
        }

        if(yTouch > baseYTop) {
            rotateDeg += 180;
        }

        // A bug caused by firing missiles at around the same y coord as top of the base was making
        // me go insane, hence the "insane" boolean which fixes the problem.
        if (Math.abs(yVelocity) < 20) {
            insane = true;
        }
        else {
            insane = false;
        }

        // Determines if the missile is travelling upwards or downwards relative to the spawn point
        // at the top of the base, which allows missiles to be fired downwards, since the explosion
        // code needs to be adjusted accordingly.
        if (yDest < baseYTop) {
            up = true;
        }
        else {
            up = false;
        }
    }
    void update(long fps){
        // Move the missile based upon the horizontal and vertical speed and the current frame rate
        if (!exploding) {
            // Update missile location
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
        // Depending on if the missile is going up or down, or is insane, calculate if the missile has
        // reached its target based on its coordinates. If it has, call explode().
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

    public void explode() {
        exploding = true;
        if (explodeRect == null) {
            sound.explode();
            explodeRect = new RectF(xDest - radius, yDest - radius, xDest + radius,
                    yDest + radius);
        }

        // calculates circles that will be used to draw the multi-colored, circular explosion that
        // occurs once the explode function is called when the missile reaches its target destination.
        
        // Switch statement logic inspired by:
        // https://github.com/leonardo-ono/JavaMissileCommandGame
        yield:
        while (exploding) {
            switch(sequencePtr) {
                case 0:
                    radius+=RADIUS_INC;
                    explodeRect.left-=RADIUS_INC;
                    explodeRect.right+=RADIUS_INC;
                    explodeRect.top-=RADIUS_INC;
                    explodeRect.bottom+=RADIUS_INC;
                    sequencePtr = 1;
                    break yield;

                case 1:
                    if (radius < MAX_EXPL_RADIUS) {
                        sequencePtr = 0;
                        break yield;
                    }
                    sequencePtr = 2;
                    break yield;

                case 2:
                    radius-=RADIUS_INC;
                    explodeRect.left+=RADIUS_INC;
                    explodeRect.right-=RADIUS_INC;
                    explodeRect.top+=RADIUS_INC;
                    explodeRect.bottom-=RADIUS_INC;
                    sequencePtr = 3;
                    break yield;

                case 3:
                    if (radius > MIN_EXPL_RADIUS){
                        sequencePtr = 2;
                        break yield;
                    }
                    waitTime = System.currentTimeMillis();
                    sequencePtr = 4;
                    break yield;

                case 4:
                    while ( System.currentTimeMillis() - waitTime < 50){
                        break yield;
                    }
                    sequencePtr = 5;


                case 5:
                    doneExploding = true;
                    exploding = false;
                    break yield;
            }
        }
    }

}
