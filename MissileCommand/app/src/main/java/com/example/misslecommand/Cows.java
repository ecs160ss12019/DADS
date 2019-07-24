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
    private float width = 40;   // Width of the cow
    private float height = 40;  // Height of the cow
    public int xPosition;       // The x position of the middle of the left side of the rectangle that represents the cow
    public int yPosition;       // The y position of the middle of the left side of the rectangle that represents the cow
    Bitmap mBitmap;

    /*
        The constructor for the Cow object. It sets the status of the cow to True (cow is alive), and takes in
        the x and y coordinates of the location of where to draw the cow
     */
    public Cows(int x, int y, Context context) {
        deathSound = MediaPlayer.create(context, R.raw.death);
        status = true;
        xPosition = x;
        yPosition = y;

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cow);

        mRect = new RectF((float)x - width/2, (float)y + height/2 - 85, (float)x + width/2,
                (float)y - height/2 - 85);
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

}
