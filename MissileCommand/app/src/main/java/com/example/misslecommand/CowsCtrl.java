package com.example.misslecommand;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.content.Context;

public class CowsCtrl {
    public int count = 0;
    public int cowNum = 6;
    public Cows[] cows;

    public CowsCtrl(int screenY, Context context, Sound sound) {
        cows = new Cows[cowNum];
        int cowX = 70;
        int cowY = screenY;
        for (int i = 0; i < cowNum; i++) {
            cows[i] = new Cows(cowX, cowY, context, sound);
            if (i != 2) {
                cowX = cowX + 300;
            }
            else {
                cowX = cowX + 450;
            }
        }
    }

    public int getCowsAlive(){
        int cowsAlive = 0;
        for (int i = 0; i < cows.length; i++) {
            if (cows[i].status) {
                cowsAlive++;
            }
        }
        return cowsAlive;
    }

    public void draw(Canvas canvas, Paint paint) {
        for (int i = 0; i < cowNum; i++) {
            if (cows[i].status) {
                if (count > 350){
                    count = 0;
                }
                if (count < 175){
                    canvas.drawBitmap(cows[i].getBitmap(), cows[i].getRect().left, cows[i].getRect().top, paint);
                }
                else{
                    canvas.drawBitmap(cows[i].getBitmap2(), cows[i].getRect().left, cows[i].getRect().top, paint);
                }
                count++;
            }
        }
    }
}
