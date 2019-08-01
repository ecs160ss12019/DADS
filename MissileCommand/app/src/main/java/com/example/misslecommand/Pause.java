package com.example.misslecommand;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

// This is the pause class. It is responsible for pausing the game and calling Options which draws
// the buttons that allow the user to toggle audio on/off and restart, and drawing the pause button.

public class Pause {
    public int xPosition;
    public int yPosition;

    public Options option;
    public Context context;
    public RectF mRect;
    public int width = 200;

    Bitmap bitmap;

    public Pause (int maxX, int maxY, Context con) {
        xPosition = maxX;
        yPosition = maxY;
        context = con;
        option = new Options(xPosition, yPosition, context);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause);
        mRect = new RectF(maxX - width, 0, maxX, width);
    }

    // This draw function is called every time the MissileCommand draw function is cycled, when the
    // game is paused or not, Pause.draw draws the circular pause button at the top right of the screen
    // and if the game IS paused, then it will call the options.draw function too.
    public void draw(Canvas canvas, Paint paint, int state) {
        canvas.drawBitmap(bitmap, mRect.left, mRect.top, paint);

        if (state == 4) {
            canvas.drawText("PAUSED", xPosition/3, yPosition/3, paint);
            option.draw(canvas, paint);
        }
    }
}
