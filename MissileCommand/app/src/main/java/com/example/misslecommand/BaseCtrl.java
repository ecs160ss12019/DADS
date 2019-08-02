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

/*
 * The BaseCtrl class is instantiated by the MissileCommand.java class at the start of the game.
 * This class is responsible for drawing the Missiles and the Missile explosions.
 */
public class BaseCtrl {
    public int count = 0;
    public int count2 = 0;
    public Base base;
    Bitmap mBitmapForMissile;
    Bitmap mBitmapForMissile2;
    public RectF mRectForMissile;


    public Paint paint;

    public BaseCtrl(int centerScreenX, int screenY, Context con, Sound sound) {
        base = new Base(centerScreenX, screenY - 100, con, sound);
        mBitmapForMissile = BitmapFactory.decodeResource(con.getResources(), R.drawable.missile1);
        mBitmapForMissile2 = BitmapFactory.decodeResource(con.getResources(), R.drawable.missile2);
    }

    public void draw(Canvas canvas, Paint paint) {
        if (count>20){
            count = 0;
        }

        // Draw the Base and it's flashing lights
        if (count < 10){
            canvas.drawBitmap(base.getBitmap2(), base.getRect().left, base.getRect().top, paint);
        } else {
            canvas.drawBitmap(base.getBitmap(), base.getRect().left, base.getRect().top, paint);
        }
        count++;

        // Go through the Missile array in Base class and check if it is exploding.
        // If so, draw the explosion, else, draw the missile flying to it's destination
        for (int i = 0; i < base.missiles.size(); i++) {
            if (base.missiles.get(i).exploding) {
                // Initialize color to RED
                int OPAC = 255;
                int RED = 255;
                int GREEN = 0;
                int BLUE = 0;

                // Update the RGB values so that the explosion will have a gradient of RED to ORANGE
                for (int concentric_rad = base.missiles.get(i).radius; concentric_rad > base.missiles.get(i).MIN_EXPL_RADIUS; concentric_rad -= 8 ) {
                    paint.setColor(Color.argb(OPAC, RED, GREEN, BLUE));
                    canvas.drawCircle(base.missiles.get(i).xDest, base.missiles.get(i).yDest,
                            concentric_rad, paint);
                    GREEN += 20;
                    BLUE += 5;
                }

            } else {
                paint.setColor(Color.argb(255, 255, 255, 255));
                canvas.drawLine(base.xCenter,
                        base.yTop,
                        base.missiles.get(i).xCenter,
                        base.missiles.get(i).yCenter,
                        paint);

                canvas.save();
                canvas.rotate( (float)base.missiles.get(i).rotateDeg, base.missiles.get(i).xCenter, base.missiles.get(i).yCenter);

                if (count2 > 50){
                    count2 = 0;
                }
                if (count2 < 25){
                    canvas.drawBitmap(mBitmapForMissile2, base.missiles.get(i).mRect.left-23, base.missiles.get(i).mRect.top-20, paint);
                }
                else {
                    canvas.drawBitmap(mBitmapForMissile2, base.missiles.get(i).mRect.left-23, base.missiles.get(i).mRect.top-20, paint);
                }
                count2++;

                canvas.restore();



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
