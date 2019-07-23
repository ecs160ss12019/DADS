package com.example.misslecommand;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.*;

public class HornetCtrl {
    public List<Hornets> hornets;
    Random random;

    public HornetCtrl() {
        hornets = new ArrayList<>();
        random = new Random();
    }

    public void draw(Canvas canvas, Paint paint) {
        for (int i = 0; i < hornets.size(); i++) {
            canvas.drawRect(hornets.get(i).mRect, paint);
            canvas.drawLine(hornets.get(i).initX,
                    hornets.get(i).initY,
                    hornets.get(i).xPosition,
                    hornets.get(i).yPosition,
                    paint);
        }
    }

    public void spawnHornets(int level, CowsCtrl cowsCtrl, int screenX) {
        int randCow = random.nextInt(cowsCtrl.cowNum);
        int didFire = random.nextInt(100);
        if (didFire <= level) {
            Cows target = cowsCtrl.cows[randCow];
            hornets.add(new Hornets(random.nextInt(screenX), 0, target, level));
        }
    }

    public void spawnSingle(int level, CowsCtrl cowsCtrl, int screenX) {
        int randCow = random.nextInt(cowsCtrl.cowNum);
        Cows target = cowsCtrl.cows[randCow];
        hornets.add(new Hornets(random.nextInt(screenX), 0, target, level));
    }


    public void update(long fps, int level, CowsCtrl cowsCtrl, int screenX) {
        for (int i = 0; i < hornets.size(); i++) {
            hornets.get(i).update(fps);
            hornets.get(i).kill();
            if (hornets.get(i).status == false) {
                hornets.remove(i);
            }
        }

        spawnHornets(level, cowsCtrl, screenX);
    }

}
