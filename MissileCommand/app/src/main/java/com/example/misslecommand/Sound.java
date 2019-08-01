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
    float volume;
    int levelChange = 8;

    public Sound (Context con, LevelCtrl lvl) {
        volume = 1f;
        context = con;
        levelCtrl = lvl;
        menu = MediaPlayer.create(context, R.raw.menu);
        menu.setVolume(volume, volume);
        start = MediaPlayer.create(context, R.raw.start);
        start.setVolume(volume, volume);
        background = MediaPlayer.create(context, R.raw.background);
        background.setVolume(volume, volume);
        background2 = MediaPlayer.create(context, R.raw.background2);
        background2.setVolume(volume, volume);
        menu.setLooping(true);
        play(menu);
    }

    public void play(MediaPlayer choice) {
        if (choice == background) {
            if (!background2.isPlaying() && levelCtrl.level >= levelChange) {
                background2.setVolume(volume, volume);
                background2.start();
            }
            else if (!background.isPlaying()) {
                background.setVolume(volume, volume);
                background.start();
            }
        }
        else if (!choice.isPlaying()) {
            choice.setVolume(volume, volume);
            choice.start();
        }
    }

    public void toggle() {
        if (volume == 1f) {
            volume = 0f;
        }
        else if (volume == 0f) {
            volume = 1f;
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
        if (volume == 0f) {
            return;
        }
        MediaPlayer launch = MediaPlayer.create(context, R.raw.fire);
        launch.setVolume(volume, volume);
        launch.start();
        launch.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.reset();
                mediaPlayer.release();
            }
        });
    }

    public void explode() {
        MediaPlayer explode = MediaPlayer.create(context, R.raw.explode);
        explode.setVolume(volume, volume);
        if (volume == 1f) {
            explode.setVolume(0.7f, 0.7f);
        }
        explode.start();
        explode.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.reset();
                mediaPlayer.release();
            }
        });
    }

    public void ammo() {
        MediaPlayer ammo = MediaPlayer.create(context, R.raw.ammo);
        ammo.setVolume(volume, volume);
        ammo.start();
        ammo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.reset();
                mediaPlayer.release();
            }
        });
    }

    public void squish() {
        MediaPlayer squish = MediaPlayer.create(context, R.raw.squish);
        squish.setVolume(volume, volume);
        squish.start();
        squish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.reset();
                mediaPlayer.release();
            }
        });
    }

    public void death() {
        MediaPlayer moo = MediaPlayer.create(context, R.raw.death);
        moo.setVolume(volume, volume);
        moo.start();
        moo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.reset();
                mediaPlayer.release();
            }
        });
    }

}
