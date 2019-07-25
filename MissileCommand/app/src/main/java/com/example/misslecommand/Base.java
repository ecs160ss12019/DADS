package com.example.misslecommand;

// This is the base class. It is responsible for firing missiles at the hornets.

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import java.util.*;

public class Base{

    public Context context;
    public boolean status;
    public RectF mRect;
    private float width = 100;
    private float height = 100;
    public float xCenter;
    public float yBottom;
    public float yTop;
    
    public int ammo;
    Bitmap mBitmap;

    public List<Missile> missiles;

    public Base(float centerScreenX, float screenY, Context con) {
        status = true;
        context = con;
        missiles = new ArrayList<>();
        xCenter = centerScreenX;
        yBottom = screenY;
        yTop = yBottom - height;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.base);

        mRect = new RectF( xCenter - width/2, yTop, xCenter + width/2, yBottom);
    }

    //will create an instance of missile and then from TouchEvent fire it to a certain location
    public void fire(int xTouch, int yTouch){
        if (ammo <= 0 || yTouch > yTop - 10) {
            //out of ammo
            return;
        }
        missiles.add(new Missile(xCenter, yTop, xTouch, yTouch, context));
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

    RectF getRect(){
        return mRect;
    }

    Bitmap getBitmap(){
        return mBitmap;
    }

}
