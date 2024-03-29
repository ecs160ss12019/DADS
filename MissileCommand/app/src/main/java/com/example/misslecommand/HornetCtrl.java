package com.example.misslecommand;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.*;

/*
 * The HornetCtrl.java class is instantiated in the MissileCommand.java class and is
 * used to spawn the hornets to the game.
 * The number of hornets spawned is probablistically determined; the higher level the
 * player gets to the more probable a hornet is to spawn.
 */

public class HornetCtrl {
    Random random;
    Context contxt;
    public int count =0;
    public List<Hornets> hornets;
    public boolean removeHornet = false;
    public int hornetsToSpawn;
    public int spawnTimer = 0;

    /*
        HornetCtrl constructor, initializes context and ammo and creates an ArrayList that will store all the hornets that are
        on the screen currently
     */
    public HornetCtrl(Context context, int ammo) {
        contxt = context;
        hornetsToSpawn = ammo;
        hornets = new ArrayList<>();
        random = new Random();
    }

    /*
     * Draws the hornets on its spawn location by accessing the Hornets.java class
     */
    public void draw(Canvas canvas, Paint paint) {
        for (int i = 0; i < hornets.size(); i++) {
            paint.setColor(Color.argb(255, 255, 255, 255));
            canvas.drawLine(hornets.get(i).initX,
                    hornets.get(i).initY,
                    hornets.get(i).xPosition,
                    hornets.get(i).yPosition,
                    paint);

            canvas.save();
            canvas.rotate( (float)hornets.get(i).rotateDeg, hornets.get(i).xPosition, hornets.get(i).yPosition);    // Rotate the canvas depending on the hornet's trajectory and draw it

            // A counter is used to alternate between the two hornet .png files to emulate a sprite
            if (count > 100){
                count = 0;
            }
            if (count < 50){
                canvas.drawBitmap(hornets.get(i).getBitmap2(), hornets.get(i).getRect().left, hornets.get(i).getRect().top, paint);
            }
            else {
                canvas.drawBitmap(hornets.get(i).getBitmap(), hornets.get(i).getRect().left, hornets.get(i).getRect().top, paint);
            }
            count++;
            canvas.restore();
        }
    }

    //Probablistically spawn hornets
    public void spawnHornets(int level, CowsCtrl cowsCtrl, int screenX, Context context) {
        int randCow = random.nextInt(cowsCtrl.cowNum);
        int didFire = random.nextInt(100);
        if (didFire <= level && hornetsToSpawn > 0) {
            Cows target = cowsCtrl.cows[randCow];
            hornets.add(new Hornets(random.nextInt(screenX), 0, target, level, context));
            hornetsToSpawn--;
            spawnTimer = 0;
        }
    }

    // Call the update function on Hornet.java
    public void update(long fps, int level, CowsCtrl cowsCtrl, int screenX) {
        for (int i = 0; i < hornets.size(); i++) {
            hornets.get(i).update(fps);
            hornets.get(i).kill();
            if (hornets.get(i).status == false) {
                hornets.remove(i);
                removeHornet = true;
            }
        }
        spawnTimer++;
        if(spawnTimer >= 33) {  // Have a chance to spawn a hornet every 33 frames
            spawnHornets(level, cowsCtrl, screenX, contxt);
        }
    }
}
