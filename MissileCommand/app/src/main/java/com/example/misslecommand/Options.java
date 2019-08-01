package com.example.misslecommand;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Options extends Activity {

    public int xPosition;
    public int yPosition;

    public RectF audioRect;
    public RectF restartRect;

    public float width = 902;
    public float height = 305;

    public Bitmap audioOnBit;
    public Bitmap audioOffBit;
    public Bitmap restartBit;

    private boolean audioOn;

    Canvas cnvs;
    Paint pnt;

    public Options(int x, int y, Context context){
        xPosition = x;
        yPosition = y;
        audioOn = true;
        audioRect = new RectF((float)x/2 - width/2, (float)(2 * y)/8 - height/2,
                (float)x/2 + width/2, (float)(2 * y)/8 + height/2);
        restartRect = new RectF((float)x/2 - width/2, (float)(5 * y)/8 - height/2,
                (float)x/2 + width/2, (float)(5 * y)/8 + height/2);

        audioOnBit = BitmapFactory.decodeResource(context.getResources(), R.drawable.on);
        audioOffBit = BitmapFactory.decodeResource(context.getResources(), R.drawable.off);
        restartBit = BitmapFactory.decodeResource(context.getResources(), R.drawable.restart);

    }

    public int touch(float x, float y) { // if 1 toggle audio, if 2 restart, if 3 back
        if (audioRect.contains(x,y)) {
            if (audioOn) {
                audioOn = false;
            }
            else {
                audioOn = true;
            }
            return 1;
        }
        else if (restartRect.contains(x,y)) {
            return 2;
        }
        return 3;
    }

    public void draw(Canvas canvas, Paint paint){
        cnvs = canvas;
        pnt = paint;
        //canvas.drawBitmap(audioBit, audioRect.left, audioRect.top, paint);
        canvas.drawBitmap(restartBit, restartRect.left, restartRect.top, paint);
        if (audioOn) {
            canvas.drawBitmap(audioOnBit, audioRect.left, audioRect.top, paint);
        }
        else {
            canvas.drawBitmap(audioOffBit, audioRect.left, audioRect.top, paint);
        }
        //canvas.drawRect(audioRect, paint);
        //canvas.drawRect(restartRect, paint);

    }
}
