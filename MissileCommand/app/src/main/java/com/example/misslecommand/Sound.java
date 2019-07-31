package com.example.misslecommand;

import android.content.Context;
import android.media.MediaPlayer;


public class Sound {
    Context context;
    MediaPlayer menu;
    MediaPlayer start;

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
}
