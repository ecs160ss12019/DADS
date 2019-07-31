package com.example.misslecommand;

import android.content.Context;
import android.media.MediaPlayer;

// Music Credit: https://patrickdearteaga.com.

public class Sound {
    Context context;
    MediaPlayer menu;
    MediaPlayer start;
    MediaPlayer background;
    MediaPlayer background2;
    LevelCtrl levelCtrl;
    int levelChange = 8;

    public Sound (Context con, LevelCtrl lvl, int state) {
        context = con;
        levelCtrl = lvl;
        menu = MediaPlayer.create(context, R.raw.menu);
        start = MediaPlayer.create(context, R.raw.start);
        background = MediaPlayer.create(context, R.raw.background);
        background2 = MediaPlayer.create(context, R.raw.background2);
        menu.setLooping(true);
        play(menu, state);
    }

    public void play(MediaPlayer choice, int state) {
        if (choice == background) {
            if (!background2.isPlaying() && levelCtrl.level >= levelChange) {
                background2.start();
            }
            else if (!background.isPlaying()) {
                background.start();
            }
        }
        else if (!choice.isPlaying()) {
            choice.start();
        }
    }

    public void pause(MediaPlayer choice) {
        if (levelCtrl.level >= levelChange && choice == background) {
            if (background2.isPlaying()) {
                background2.pause();
            }
            if (background.isPlaying()) {
                background.pause();
            }
        }
        else if (choice.isPlaying()) {
            choice.pause();
        }

    }

    // Launch, Explode, Cow Death, and Ammo are not class variables because I could not figure out how else to allow
    // multiple launching/exploding noises to play at once, whereas menu and start only need one at
    // a time.
    public void launch() {
        MediaPlayer launch = MediaPlayer.create(context, R.raw.fire);
        launch.start();
    }

    public void explode() {
        MediaPlayer explode = MediaPlayer.create(context, R.raw.explode);
        explode.setVolume(0.7f,0.7f);
        explode.start();
    }

    public void ammo() {
        MediaPlayer ammo = MediaPlayer.create(context, R.raw.ammo);
        ammo.start();
    }

    public void squish() {
        MediaPlayer squish = MediaPlayer.create(context, R.raw.squish);
        squish.start();
    }

    public void death() {
        MediaPlayer moo = MediaPlayer.create(context, R.raw.death);
        moo.start();
    }
}
