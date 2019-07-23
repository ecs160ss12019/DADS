package com.example.misslecommand;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.*;

public class BaseCtrl {
    public Base base;

    public BaseCtrl(int centerScreenX, int screenY) {
        base = new Base(centerScreenX, screenY);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawRect(base.mRect, paint);

        for (int i = 0; i < base.missiles.size(); i++) {
            if (base.missiles.get(i).exploding) {
                canvas.drawRect(base.missiles.get(i).explodeRect, paint);
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
