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
    int levelChange = 10;

    public Sound (Context con, LevelCtrl lvl) {
        context = con;
        levelCtrl = lvl;
        menu = MediaPlayer.create(context, R.raw.menu);
        start = MediaPlayer.create(context, R.raw.start);
        background = MediaPlayer.create(context, R.raw.background);
        background2 = MediaPlayer.create(context, R.raw.background2);
        menu.setLooping(true);
    }

    public void play(MediaPlayer choice) {
        if (!choice.isPlaying()) {
            if (choice == background) {
                while (start.isPlaying()) {
                    choice.seekTo(0);
                }
                if (levelCtrl.level >= levelChange) {
                    choice = background2;
                }
            }
            choice.start();
        }
    }

    public void pause(MediaPlayer choice) {
        if (levelCtrl.level >= levelChange) {
            choice = background2;
        }
        if (choice.isPlaying()) {
            choice.pause();
        }

    }

    // Launch, Explode, and Cow Death are not class variables because I could not figure out how else to allow
    // multiple launching/exploding noises to play at once, whereas menu and start only need one at
    // a time.
    public void launch() {
        MediaPlayer launch = MediaPlayer.create(context, R.raw.fire);
        launch.start();
    }

    public void explode() {
        MediaPlayer explode = MediaPlayer.create(context, R.raw.explode);
        explode.start();
    }

    public void death() {
        MediaPlayer moo = MediaPlayer.create(context, R.raw.death);
        moo.start();
    }
}
