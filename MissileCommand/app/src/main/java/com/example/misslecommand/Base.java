package com.example.misslecommand;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import java.util.*;

/*
 * The Base Class is responsible for firing missiles at the hornets.
 * It draws the missiles on the screen and keeps track of the ammo count
 */

public class Base{
    Sound sound;
    public Context context;
    public RectF mRect;

    private float width = 300;
    private float height = 300;
    public float xCenter;
    public float yBottom;
    public float yTop;
    
    public int ammo;
    public boolean status;

    Bitmap mBitmap;
    Bitmap mBitmap2;

    public List<Missile> missiles;

    public Base(float centerScreenX, float screenY, Context con, Sound snd) {
        missiles = new ArrayList<>();
        sound = snd;
        context = con;
        status = true;

        xCenter = centerScreenX;
        yBottom = screenY;
        yTop = yBottom - height;

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.base);
        mBitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.base2);

        mRect = new RectF( xCenter - width/2, yTop, xCenter + width/2, yBottom);
    }

    // Creates an instance of missile and then from TouchEvent fire it to a certain location
    // Decrements ammo
    public void fire(int xTouch, int yTouch){
        if (ammo <= 0) {
            //out of ammo
            return;
        }
        missiles.add(new Missile(xCenter, yTop, xTouch, yTouch, sound));
        ammo--;
    }

    public void update() {
        for (int i = 0; i < missiles.size(); i++) {
            if (missiles.get(i).doneExploding) {
                missiles.remove(i);
            }
        }
    }

    RectF getRect(){
        return mRect;
    }

    Bitmap getBitmap(){
        return mBitmap;
    }
    Bitmap getBitmap2(){
        return mBitmap2;
    }

}
