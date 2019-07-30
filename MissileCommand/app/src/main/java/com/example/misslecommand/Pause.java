package com.example.misslecommand;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Pause {
    public Context context;
    public boolean status;
    public RectF mRect;
    public int width = 130;

    Bitmap bitmap;

    public Pause (float maxX, Context con) {
        context = con;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause);
        mRect = new RectF(maxX - width, 0, maxX, width);

    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap, mRect.left, mRect.top, paint);
        //canvas.drawRect(mRect, paint);
    }
}
