package com.example.misslecommand;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.*;

public class BaseCtrl {
    public int count = 0;
    public int count2 = 0;
    public Base base;
    Bitmap mBitmapForMissile;
    Bitmap mBitmapForMissile2;
    public RectF mRectForMissile;


    public Paint paint;

    public BaseCtrl(int centerScreenX, int screenY, Context con, Sound sound) {
        base = new Base(centerScreenX, screenY, con, sound);
        mBitmapForMissile = BitmapFactory.decodeResource(con.getResources(), R.drawable.missile1);
        mBitmapForMissile2 = BitmapFactory.decodeResource(con.getResources(), R.drawable.missile2);

        //mRectForMissile = new RectF( centerScreenX*2 - 40/2, screenY-100, centerScreenX*2 + 40/2, screenY);

    }

    public void draw(Canvas canvas, Paint paint) {
        //canvas.drawRect(base.mRect, paint);
        if (count>20){
            count = 0;
        }
        if (count < 10){
            canvas.drawBitmap(base.getBitmap2(), base.getRect().left, base.getRect().top, paint);
        }

        else {
            canvas.drawBitmap(base.getBitmap(), base.getRect().left, base.getRect().top, paint);
        }

        count++;
        //canvas.drawRect(base.mRect, paint);

        for (int i = 0; i < base.missiles.size(); i++) {
            Log.d("BaseCtrl", "looping through missiles");

            if (base.missiles.get(i).exploding) {
                Log.d("BaseCtrl", "if missiles.exploding");
                //canvas.drawRect(base.missiles.get(i).explodeRect, paint);
                int OPAC = 255;
                int RED = 255;
                int GREEN = 0;
                int BLUE = 0;
                for (int concentric_rad = base.missiles.get(i).radius; concentric_rad > base.missiles.get(i).MIN_EXPL_RADIUS; concentric_rad -= 3 ) {
                    paint.setColor(Color.argb(OPAC, RED, GREEN, BLUE));
                    canvas.drawCircle(base.missiles.get(i).xDest, base.missiles.get(i).yDest,
                            concentric_rad, paint);
                    GREEN += 20;
                    BLUE += 5;
                }

            } else {
                //canvas.drawRect(base.missiles.get(i).mRect, paint);
                if (count2 > 50){
                    count2 = 0;
                }
                if (count2 < 25){
                    canvas.drawBitmap(mBitmapForMissile2, base.missiles.get(i).mRect.left, base.missiles.get(i).mRect.top, paint);
                }
                else {
                    canvas.drawBitmap(mBitmapForMissile2, base.missiles.get(i).mRect.left, base.missiles.get(i).mRect.top, paint);
                }
                count2++;


                paint.setColor(Color.argb(255, 255, 255, 255));
                canvas.drawLine(base.xCenter,
                        base.yTop,
                        base.missiles.get(i).xCenter,
                        base.missiles.get(i).yCenter,
                        paint);
            }
        }
    }

    public void update(long fps) {
        for (int i = 0; i < base.missiles.size(); i++) {
            base.missiles.get(i).update(fps);
        }
        base.update();
    }

    RectF getRect(){
        return mRectForMissile;
    }

    Bitmap getmBitmapForMissile(){
        return mBitmapForMissile;
    }

    Bitmap getmBitmapForMissile2(){
        return mBitmapForMissile2;
    }
}
