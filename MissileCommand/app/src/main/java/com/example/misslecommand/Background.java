package com.example.misslecommand;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.content.Context;

// This is the game background class. This loads, and draws the background image behind the game.
// It scales the image based on the size of screen. It is also responsible for the Game Over screen
public class Background {

    Bitmap mBitmap;
    Bitmap scaledBitmap;
    Bitmap gameOverMap;
    Bitmap scaledGameOver;
    public RectF mRect;

    public Background(int screenX, int screenY, Context context) {
        mRect = new RectF(0, 0, screenX, screenY);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        scaledBitmap = Bitmap.createScaledBitmap(mBitmap, screenX, screenY, true);

        gameOverMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gameover);
        scaledGameOver = Bitmap.createScaledBitmap(gameOverMap, screenX, screenY, true);

    }

    // Drawing Function
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(scaledBitmap, mRect.left, mRect.top, paint);
    }

    public void drawGameOver(Canvas canvas, Paint paint) {
        canvas.drawBitmap(scaledGameOver, mRect.left, mRect.top, paint);
    }
}
