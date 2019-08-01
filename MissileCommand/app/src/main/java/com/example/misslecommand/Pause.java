package com.example.misslecommand;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

// This is the pause class. It is responsible for pausing the game and calling Options which draws
// the buttons that allow the user to change in game experiences.

public class Pause {
    public int xPosition;
    public int yPosition;

    public Options option;
    public Context context;
    public boolean status;
    public RectF mRect;
    public int width = 130;

    Bitmap bitmap;

    public Pause (int maxX, int maxY, Context con) {
        xPosition = maxX;
        yPosition = maxY;
        context = con;
        option = new Options(xPosition, yPosition, context);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause);
        mRect = new RectF(maxX - width, 0, maxX, width);
    }

    public void draw(Canvas canvas, Paint paint, int state) {
        canvas.drawBitmap(bitmap, mRect.left, mRect.top, paint);

        if (state == 4) {
            canvas.drawText("PAUSED", xPosition/3, yPosition/3, paint);
            option.draw(canvas, paint);
        }
    }
}
