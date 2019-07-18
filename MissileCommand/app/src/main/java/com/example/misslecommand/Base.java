package com.example.misslecommand;

// This is the base class. It is responsible for firing missiles at the hornets.

import android.graphics.RectF;
import java.util.*;

public class Base{

    public boolean status;
    public RectF mRect;
    private float width = 100;
    private float height = 100;
    public int xPosition;
    public int yPosition;
    
    public int ammo;

    public List<Missile> missiles;

    public Base(int x, int y) {
        status = true;
        missiles = new ArrayList<>();
        xPosition = x;
        yPosition = y;
        mRect = new RectF((float)x - width/2, (float)y + height/2, (float)x + width/2,
                (float)y - height/2);
    }

    //will create an instance of missile and then from TouchEvent fire it to a certain location
    public void fire(int xTouch, int yTouch){
        if (ammo <= 0) {
            //out of ammo
            return;
        }
        missiles.add(new Missile(xPosition, yPosition, xTouch, yTouch));
        ammo--;
    }

    public void update() {
        for (int i = 0; i < missiles.size(); i++) {
            if (missiles.get(i).done) {
                missiles.remove(i);
            }
        }
    }

    public void collision(){

    }

    public void draw(){

    }

}
