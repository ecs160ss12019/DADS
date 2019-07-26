package com.example.misslecommand;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.*;

public class BaseCtrl {
    public Base base;

    public BaseCtrl(int centerScreenX, int screenY, Context con) {
        base = new Base(centerScreenX, screenY, con);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(base.getBitmap(), base.getRect().left, base.getRect().top, paint);
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
