package com.example.misslecommand;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.content.Context;

/*
 * The Cow Class is responsible for creating and removing the Cow objects
 */

public class Cows {
    Sound sound;
    public boolean status;      // The status of whether the cow is alive or not alive
    public RectF mRect;         // The rectangle that will represent the cow's hit box
    public float width = 110;   // Width of the cow
    public float height = 164;  // Height of the cow
    public int xPosition;       // The x position of the middle of the left side of the rectangle that represents the cow
    public int yPosition;       // The y position of the middle of the left side of the rectangle that represents the cow
    Bitmap mBitmap;
    Bitmap mBitmap2;

    public Cows(int x, int screenY, Context context, Sound snd) {
        sound = snd;
        status = true;
        xPosition = x;
        yPosition = screenY;

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cow);
        mBitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.cow2);


        mRect = new RectF((float)x - width/2, (float)screenY - height, (float)x + width/2,
                (float)screenY);
    }

    // This function will set status to false and call sound to play the cow death sound effect.
    // The draw function in CowsCtrl will not draw cows with status == false.
    public void kill(){
        if (status) {
            sound.death();
        }
        status = false;
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
