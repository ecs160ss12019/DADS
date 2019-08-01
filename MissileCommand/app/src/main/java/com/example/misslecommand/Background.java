package com.example.misslecommand;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.content.Context;

// This is the game background class. This loads, and draws the background image behind the game.
// It scales the image based on the size of screen.
public class Background {

    Bitmap mBitmap;
    Bitmap scaledBitmap;
    public RectF mRect;

    // Constructor
    public Background(int screenX, int screenY, Context context) {
        mRect = new RectF(0, 0, screenX, screenY);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        scaledBitmap = Bitmap.createScaledBitmap(mBitmap, screenX, screenY, true);

    }

    RectF getRect(){
        return mRect;
    }

    Bitmap getBitmap(){
        return scaledBitmap;
    }

    // Drawing Function
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(this.getBitmap(), this.getRect().left, this.getRect().top, paint);
    }
}
