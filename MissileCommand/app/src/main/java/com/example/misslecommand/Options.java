package com.example.misslecommand;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

// This class is responsible for drawing the mini menu shown while the game is paused, that being
// buttons for toggling audio, and restarting the game, as well as unpausing if the player taps
// anywhere other than the buttons. After determining where the player taps, it returns an int to
// MissileCommand which acts according to the int given, like an HTML response code in a way.

// I made this class as a sort of "custom" menu that is made as an overlay on the screen rather than
// a more vanilla android type menu because I believe it makes the game flow better, because when the
// game is paused it still shows the player what their game currently looks like, instead of being moved
// to a completely different screen.

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
        // Sets the audioOn boolean accordingly to be used in the draw function to determine which
        // audio button to draw, either the on or the off one.
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
        canvas.drawBitmap(restartBit, restartRect.left, restartRect.top, paint);
        // Draws the appropriate audio button depending on which one is being displayed when tapped,
        // switching to the other one when touched.
        if (audioOn) {
            canvas.drawBitmap(audioOnBit, audioRect.left, audioRect.top, paint);
        }
        else {
            canvas.drawBitmap(audioOffBit, audioRect.left, audioRect.top, paint);
        }
    }
}
