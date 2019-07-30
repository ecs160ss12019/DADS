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
    Bitmap bitMapForTitle;
    Bitmap scaledBitMapForTitle;
    Bitmap bitMapForSubTitle;
    Bitmap scaledBitMapForSubTitle;

    private int mFontSize;

    Rect bounds = new Rect();
    public float mt;
    public int bw;

    Canvas cnvs;
    Paint pnt;

    String title = "DADS";
    String subTitle = "Davis Aerial Defense System";
    String tapToStart = "Tap to Start";


    public MainMenu(int x, int y, Context context){
        xPosition = x;
        yPosition = y;
        mFontSize = x / 20;
        contxt = context;

        mRect = new RectF((float)0, (float)0, (float)x/2, (float)y/2);

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu);
        scaledBitmap = Bitmap.createScaledBitmap(mBitmap, x, y, true);
        bitMapForTitle = BitmapFactory.decodeResource(context.getResources(), R.drawable.title);
        bitMapForSubTitle = BitmapFactory.decodeResource(context.getResources(), R.drawable.subtitle);

    }


    RectF getRect(){
        return mRect;
    }

    Bitmap getBitmap(){
        return scaledBitmap;
    }

    public void draw(Canvas canvas, Paint paint){
        //backgrnd.draw(mCanvas, mPaint);
        cnvs = canvas;
        pnt = paint;

        canvas.drawBitmap(this.getBitmap(), this.getRect().left, this.getRect().top, paint);
        canvas.drawBitmap(bitMapForTitle, (xPosition/2)-150, yPosition/3, paint);
        canvas.drawBitmap(bitMapForSubTitle, (xPosition/2)-50, yPosition/2, paint);
        paint.setColor(Color.argb(255, 255, 255, 255));

        //Choose the font size
        paint.setTextSize(mFontSize);

        //drawParts(title,xPosition/2, yPosition/3);
        //drawParts(subTitle, xPosition/2, yPosition*(4/5));
        //drawParts(tapToStart, xPosition/2, yPosition/2);
    }

}


