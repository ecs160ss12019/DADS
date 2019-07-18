package com.example.misslecommand;

import android.graphics.RectF;

public class Cows {

    public boolean status;      // The status of whether the cow is alive or not alive
    public RectF mRect;         // The rectangle that will represent the cow's hit box
    private float width = 40;   // Width of the cow
    private float height = 40;  // Height of the cow
    public int xPosition;       // The x position of the middle of the left side of the rectangle that represents the cow
    public int yPosition;       // The y position of the middle of the left side of the rectangle that represents the cow

    /*
        The constructor for the Cow object. It sets the status of the cow to True (cow is alive), and takes in
        the x and y coordinates of the location of where to draw the cow
     */
    public Cows(int x, int y) {
        status = true;
        xPosition = x;
        yPosition = y;

        mRect = new RectF((float)x - width/2, (float)y + height/2, (float)x + width/2,
                (float)y - height/2);
    }

    // This function will kill (remove the cow entity)
    public void kill(){
        status = false;
    }

    public void draw(){

    }

}
