package com.example.misslecommand;

import android.graphics.RectF;

public class Cows {

    private boolean status;
    public RectF mRect;
    private float hornWidth;
    private float hornHeight;
    public int xPosition;
    public int yPosition;

    // Return a reference to mRect to PongGame
    RectF getRect(){
        return mRect;
    }

    // This function will kill (remove the cow entity)
    public void kill(){

    }

    public void draw(){

    }

    public int[] getPosition(){
        int[] myPosition = new int[2];
        myPosition[0] = xPosition;
        myPosition[1] = yPosition;
        return myPosition;
    }
}
