package com.example.misslecommand;

import android.content.Context;
import android.media.MediaPlayer;


public class Sound {
    Context context;
    MediaPlayer menu;
    MediaPlayer start;
    MediaPlayer

    public Sound (Context con) {
        context = con;

        menu = MediaPlayer.create(context, R.raw.menu);
        start = MediaPlayer.create(context, R.raw.start);
        menu.setLooping(true);
    }

    public void play(MediaPlayer choice) {
        choice.start();
    }

    public void pause(MediaPlayer choice) {
        choice.pause();
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
