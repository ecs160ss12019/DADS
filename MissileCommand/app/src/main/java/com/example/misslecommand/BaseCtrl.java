package com.example.misslecommand;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.*;

public class BaseCtrl {
    public int count = 0;
    public Base base;

    public BaseCtrl(int centerScreenX, int screenY, Context con, Sound sound) {
        base = new Base(centerScreenX, screenY, con, sound);
    }

    public void draw(Canvas canvas, Paint paint) {
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

                canvas.drawCircle(base.missiles.get(i).xDest, base.missiles.get(i).yDest,
                        base.missiles.get(i).radius, paint);
            } else {
                canvas.drawRect(base.missiles.get(i).mRect, paint);
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
}
