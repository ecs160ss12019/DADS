package com.example.misslecommand;

import android.graphics.RectF;

public class Cows {

    public boolean status;
    public RectF mRect;
    private float width = 40;
    private float height = 40;
    public int xPosition;
    public int yPosition;

    public Cows() {
        status = true;
    }

    public void setCow(int x, int y) {
        xPosition = x;
        yPosition = y;

        mRect = new RectF((float)x - width/2, (float)y + height/2, (float)x + width/2,
                (float)y - height/2);
    }

    // This function will kill (remove the cow entity)
    public void kill(){

    }

    public void draw(){

    }

}
