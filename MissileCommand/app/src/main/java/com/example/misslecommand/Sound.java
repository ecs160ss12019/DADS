package com.example.misslecommand;

import android.content.Context;
import android.media.MediaPlayer;

// Music Credit: https://patrickdearteaga.com.
// Sound Effects are from freesound.org

// This class is responsible for storing, playing, and pausing sound effects and music.
// It was created based on feedback given by the professor saying that sound should be given a class.

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

    // This function is called by telling Sound which MediaPlayer to play, then sets the volume to
    // the current volume, and calling the MediaPlayer.start() function. It also has an if statement
    // at the beginning used to implement a different background music.
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

    // This function flips volume between 1f and 0f, which is called when the player turns audio on
    // or off in the pause menu.
    public void toggle() {
        if (volume == 1f) {
            volume = 0f;
        }
        else if (volume == 0f) {
            volume = 1f;
        }

    }

    // This function is the same as play, but calls the MediaPlayer.pause() function instead.
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

    // All the functions below have MediaPlayers that are not class variables because I could not figure out how else to allow
    // multiple launching/exploding noises to play at once, whereas menu and start only need one at
    // a time.
    // They all have OnCompletionListeners that releases the MediaPlayer when the sound is
    // done playing.
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

    // The explode sound effect sets volume to 0.7f instead of 1f because the explosion sound was
    // much louder than squish or ammo, and since those sound effects usually play together, it
    // would drown them out.
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

    public void gameOver() {
        MediaPlayer gameOver = MediaPlayer.create(context, R.raw.gameover);
        gameOver.setVolume(volume, volume);
        gameOver.start();
        gameOver.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.reset();
                mediaPlayer.release();
            }
        });
    }

}
