package com.example.misslecommand;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

    String title = "DADS";
    String subTitle = "Davis Aerial Defense System";
    String tapToStart = "Tap to Protect the cows from the evil hornets";


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

        Rect bounds = new Rect();

        paint.setColor(Color.RED);

        paint.getTextBounds(title, 0, title.length(), bounds);
        float mt = paint.measureText(title);
        int bw = bounds.width();

        Log.i("LCG", String.format(
                "measureText %f, getTextBounds %d (%s)",
                mt,
                bw, bounds.toShortString())
        );
        bounds.offset(0, -bounds.top);
        paint.setStyle(Paint.Style.STROKE);

        paint.setColor(Color.WHITE);
        canvas.drawText(title, (xPosition/2)-(bw/2), (yPosition/3), paint);

        //canvas.drawText("DADS", xPosition/2, yPosition/3, paint);
        //canvas.drawText("Davis Aerial Defense System!", xPosition/2, (yPosition*4)/5, paint);
    }

}
