package com.example.misslecommand;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.*;

public class BaseCtrl {
    public Base base;

    public BaseCtrl(int x, int y) {
        base = new Base(x,y);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawRect(base.mRect, paint);

        for (int i = 0; i < base.missiles.size(); i++) {
            if (base.missiles.get(i).exploding) {
                canvas.drawRect(base.missiles.get(i).explodeRect, paint);
            }
            else {
                canvas.drawRect(base.missiles.get(i).mRect, paint);
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
