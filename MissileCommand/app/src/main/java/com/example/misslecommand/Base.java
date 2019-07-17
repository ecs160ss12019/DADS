package com.example.misslecommand;

// This is the base class. It is responsible for firing missiles at the hornets.

import android.graphics.RectF;

public class Base {

    public boolean status;
    public RectF mRect;
    private float width = 100;
    private float height = 100;
    public int xPosition;
    public int yPosition;

    public int ammo;

    public Missile[] missiles;

    public Base(int x, int y) {
        status = true;
        missiles = new Missile[50];
        xPosition = x;
        yPosition = y;
        mRect = new RectF((float)x - width/2, (float)y + height/2, (float)x + width/2,
                (float)y - height/2);
    }

    //will create an instance of missile and then from TouchEvent fire it to a certain location
    public void fire(){
    }

    public void collision(){

    }

    public void draw(){

    }

}
