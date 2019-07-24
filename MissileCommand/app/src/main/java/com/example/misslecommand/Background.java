package com.example.misslecommand;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.content.Context;

public class Background {

    Bitmap mBitmap;
    public RectF mRect;

    // Constructor
    public Background(int screenX, int screenY, Context context) {
        mRect = new RectF(0, 0, screenX, screenY);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.stars);
    }

    RectF getRect(){
        return mRect;
    }

    Bitmap getBitmap(){
        return mBitmap;
    }

    // Drawing Function
    public void draw(Canvas canvas, Paint paint) {

        canvas.drawBitmap(this.getBitmap(), this.getRect().left, this.getRect().top, paint);
    }
}
