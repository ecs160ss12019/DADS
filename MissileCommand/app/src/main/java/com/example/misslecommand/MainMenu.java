package com.example.misslecommand;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.*;
import android.content.res.Resources;

public class MainMenu {

    public int xPosition;
    public int yPosition;

    public RectF mRect;
    Context contxt;
    Bitmap mBitmap;
    Bitmap scaledBitmap;

    private int mFontSize;




    public MainMenu(int x, int y, Context context){
        xPosition = x;
        yPosition = y;
        mFontSize = x / 20;
        contxt = context;

        mRect = new RectF((float)0, (float)0, (float)x/2, (float)y/2);

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu);

        scaledBitmap = Bitmap.createScaledBitmap(mBitmap, x, y, true);

    }


    RectF getRect(){
        return mRect;
    }

    Bitmap getBitmap(){
        return scaledBitmap;
    }

    public void draw(Canvas canvas, Paint paint){
        //backgrnd.draw(mCanvas, mPaint);
        canvas.drawBitmap(this.getBitmap(), this.getRect().left, this.getRect().top, paint);


        paint.setColor(Color.argb(255, 255, 255, 255));

        //Choose the font size
        paint.setTextSize(mFontSize);

        canvas.drawText("DADS", 500, 500, paint);
        canvas.drawText("Davis Aerial Defense System!", 500, 800, paint);
    }

}
