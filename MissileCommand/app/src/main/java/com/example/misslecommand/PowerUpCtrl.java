package com.example.misslecommand;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.*;

public class PowerUpCtrl {
    public List<PowerUp> powerUps;
    Random random;

    public PowerUpCtrl() {
        powerUps = new ArrayList<>();
        random = new Random();
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb
                (255, 255, 0, 0));

        for (int i = 0; i < powerUps.size(); i++) {
            canvas.drawRect(powerUps.get(i).mRect, paint);
        }
    }

    public void spawnPowerUps(int level, int screenX, int screenY) {
        int didFire = random.nextInt(1000);
        if (didFire <= level) {
            powerUps.add(new PowerUp(random.nextInt(screenX-69), 0, screenY));
        }
    }

    public void spawnSingle(int screenX, int screenY) {
        powerUps.add(new PowerUp(random.nextInt(screenX-69), 0, screenY));
    }

    public void update(long fps, int level, int screenX, int screenY) {
        for (int i = 0; i < powerUps.size(); i++) {
            powerUps.get(i).update(fps);
            powerUps.get(i).kill();
            if (powerUps.get(i).status == false) {
                powerUps.remove(i);
            }
        }

        spawnPowerUps(level, screenX, screenY);
    }

}
