package com.example.misslecommand;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.*;

public class PowerUpCtrl {
    public int count = 0;
    public List<PowerUp> powerUps;
    Random random;
    Context contxt;
    public int powerUpTimer = 0;

    public PowerUpCtrl(Context context) {
        powerUps = new ArrayList<>();
        random = new Random();
        contxt = context;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb
                (255, 255, 0, 0));

        for (int i = 0; i < powerUps.size(); i++) {
            if (count > 100){
                count = 0;
            }
            if (count > 50){
                canvas.drawBitmap(powerUps.get(i).getBitmap2(), powerUps.get(i).getRect().left, powerUps.get(i).getRect().top, paint);
            }
            else{
                canvas.drawBitmap(powerUps.get(i).getBitmap(), powerUps.get(i).getRect().left, powerUps.get(i).getRect().top, paint);
            }
            //canvas.drawBitmap(powerUps.get(i).getBitmap(), powerUps.get(i).getRect().left, powerUps.get(i).getRect().top, paint);
            count++;
            //canvas.drawRect(powerUps.get(i).mRect, paint);
        }
    }

    public void spawnPowerUps(int level, int screenX, int screenY) {
        int didFire = random.nextInt(1000);
        if (didFire <= level) {
            powerUps.add(new PowerUp(random.nextInt(screenX-69), 0, screenY, contxt));
            powerUpTimer = 0;
        }
    }

    public void spawnSingle(int screenX, int screenY) {
        powerUps.add(new PowerUp(random.nextInt(screenX-69), 0, screenY, contxt));
    }

    public void update(long fps, int level, int screenX, int screenY) {
        for (int i = 0; i < powerUps.size(); i++) {
            powerUps.get(i).update(fps);
            powerUps.get(i).kill();
            if (powerUps.get(i).status == false) {
                powerUps.remove(i);
            }
        }

        powerUpTimer++;
        if(powerUpTimer >= 33){     //have a chance to spawn a powerup every 33 frames
            spawnPowerUps(level, screenX, screenY);
        }
    }

}
