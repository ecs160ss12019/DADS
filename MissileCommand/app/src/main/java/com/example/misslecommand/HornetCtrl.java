package com.example.misslecommand;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.*;

public class HornetCtrl {
    public List<Hornets> hornets;

    public HornetCtrl() {
        hornets = new ArrayList<>();
    }

    public void draw(Canvas canvas, Paint paint) {
        for (int i = 0; i < hornets.size(); i++) {
            canvas.drawRect(hornets.get(i).mRect, paint);
        }
    }

    public void spawnHornets(int level, CowsCtrl cowsCtrl, int screenX) {
        Random random = new Random();
        int randCow = random.nextInt(cowsCtrl.cowNum);
        int didFire = random.nextInt(100);
        if (didFire <= level) {
            Cows target = cowsCtrl.cows[randCow];
            hornets.add(new Hornets(random.nextInt(screenX), 0, target, level));
        }
    }


    public void update(long fps, int level, CowsCtrl cowsCtrl, int screenX) {
        for (int i = 0; i < hornets.size(); i++) {
            hornets.get(i).update(fps);
            //hornets.get(i).detectCollisions();
            hornets.get(i).kill();
            if (hornets.get(i).status == false) {
                hornets.remove(i);
            }
        }

        spawnHornets(level, cowsCtrl, screenX);
    }

}
