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

public class Options extends Activity {

    public int xPosition;
    public int yPosition;

    public RectF audioRect;
    public RectF restartRect;
    public RectF backRect;

    public float width = 302;
    public float height = 105;

    public Bitmap audioBit;
    public Bitmap restartBit;
    public Bitmap backBit;

    Context contxt;
    Canvas cnvs;
    Paint pnt;

    public Options(int x, int y, Context context){
        xPosition = x;
        yPosition = y;
        contxt = context;

        audioRect = new RectF((float)x/2 - width/2, (float)y/8 - height/2,
                (float)x/2 + width/2, (float)y/8 + height/2);
        restartRect = new RectF((float)x/2 - width/2, (float)(3 * y)/8 - height/2,
                (float)x/2 + width/2, (float)(3 * y)/8 + height/2);
        backRect = new RectF((float)x/2 - width/2, (float)(5 * y)/8 - height/2,
                (float)x/2 + width/2, (float)(5 * y)/8 + height/2);

        //mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu);
        //scaledBitmap = Bitmap.createScaledBitmap(mBitmap, x, y, true);
        //bitMapForTitle = BitmapFactory.decodeResource(context.getResources(), R.drawable.title);
        //bitMapForSubTitle = BitmapFactory.decodeResource(context.getResources(), R.drawable.subtitle);

    }

    public void draw(Canvas canvas, Paint paint){
        //backgrnd.draw(mCanvas, mPaint);
        cnvs = canvas;
        pnt = paint;

        canvas.drawRect(audioRect, paint);
        canvas.drawRect(restartRect, paint);
        canvas.drawRect(backRect, paint);

    }
}
