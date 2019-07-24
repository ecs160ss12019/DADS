package com.example.misslecommand;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public class MainActivity extends Activity {
    private MissileCommand missileGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        missileGame = new MissileCommand(this, size.x, size.y);
        setContentView(missileGame);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // More code here later in the chapter
        missileGame.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // More code here later in the chapter
        missileGame.pause();
    }
}
