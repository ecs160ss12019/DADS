package com.example.misslecommand;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;


public class Cows {
    MediaPlayer deathSound;
    public boolean status;      // The status of whether the cow is alive or not alive
    public RectF mRect;         // The rectangle that will represent the cow's hit box
    public float width = 110;   // Width of the cow
    public float height = 164;  // Height of the cow
    public int xPosition;       // The x position of the middle of the left side of the rectangle that represents the cow
    public int yPosition;       // The y position of the middle of the left side of the rectangle that represents the cow
    Bitmap mBitmap;
    Bitmap mBitmap2;

    /*
        The constructor for the Cow object. It sets the status of the cow to True (cow is alive), and takes in
        the x and y coordinates of the location of where to draw the cow
     */
    public Cows(int x, int screenY, Context context) {
        deathSound = MediaPlayer.create(context, R.raw.death);
        status = true;
        xPosition = x;
        yPosition = screenY;

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cow);
        mBitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.cow2);


        mRect = new RectF((float)x - width/2, (float)screenY - height, (float)x + width/2,
                (float)screenY);
    }

    // This function will kill (remove the cow entity)
    public void kill(){
        if (status) {
            deathSound.start();
        }
        status = false;
    }

    public void draw(){

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
