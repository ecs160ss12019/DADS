package com.example.misslecommand;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.graphics.RectF;
import android.view.SurfaceView;
import java.util.*;

class MissileCommand extends SurfaceView implements Runnable{

    // Are we debugging?
    private final boolean DEBUGGING = true;

    MediaPlayer menuPlayer;
    MediaPlayer startPlayer;

    // These objects are needed to do the drawing
    private SurfaceHolder mOurHolder;
    private Canvas mCanvas;
    private Paint mPaint;

    // How many frames per second did we get?
    private long mFPS;
    // The number of milliseconds in a second
    private final int MILLIS_IN_SECOND = 1000;

    // Holds the resolution of the screen
    private int mScreenX;
    private int mScreenY;
    // How big will the text be?
    private int mFontSize;
    private int mFontMargin;

    // The game objects controllers
    private MainMenu mainMen;
    private Background backgrnd;
    private BaseCtrl baseCtrl;
    private CowsCtrl cowsCtrl;
    private HornetCtrl hornetCtrl;
    private PowerUpCtrl powerUpCtrl;
    private LevelCtrl levelCtrl;

    private int numPowerup;
    private int hornetsDestroyed;
    private boolean killedHornet = false;
    private boolean killedPowerup = false;

    private int state; // 0 = main menu, 1 = in game, 2 = in between levels

    // The current score and lives remaining
    private int score = 0;
    private boolean scoreAdjusted = false;
    //private int numMissiles = 10;

    // Here is the Thread and two control variables
    private Thread mGameThread = null;

    // This volatile variable can be accessed
    // from inside and outside the thread
    private volatile boolean mPlaying;
    private boolean mPaused = true;
    Context contxt;

    // The PongGame constructor
    // Called when this line:
    // mPongGame = new PongGame(this, size.x, size.y);
    // is executed from PongActivity
    public MissileCommand(Context context, int x, int y) {
        // Super... calls the parent class
        // constructor of SurfaceView
        // provided by Android
        super(context);


        menuPlayer = MediaPlayer.create(context, R.raw.menu);
        startPlayer = MediaPlayer.create(context, R.raw.start);
        menuPlayer.setVolume(80,80);
        menuPlayer.setLooping(true);
        startPlayer.setVolume(80,80);
        score = 0;
        menuPlayer.start();
        // Initialize these two members/fields
        // With the values passesd in as parameters
        mScreenX = x;
        mScreenY = y;

        // Font is 5% (1/20th) of screen width
        mFontSize = mScreenX / 20;
        // Margin is 1.5% (1/75th) of screen width
        mFontMargin = mScreenX / 75;

        // Initialize the objects
        // ready for drawing with
        // getHolder is a method of SurfaceView
        mOurHolder = getHolder();
        mPaint = new Paint();
        contxt = context;

        // Initialize the cows and base
        mainMen = new MainMenu(mScreenX, mScreenY, context);
        backgrnd = new Background(mScreenX, mScreenY, context);
        cowsCtrl = new CowsCtrl(mScreenY, context);
        baseCtrl = new BaseCtrl(mScreenX/2, mScreenY, context);
        levelCtrl = new LevelCtrl();
        state = 0;

        // Everything is ready so start the game
        draw();
    }

    // The player has just lost, beat the round
    // or is starting their first game
    private void startNewGame(){
        // Rest the score and the player's missiles
        scoreAdjusted = false;
        baseCtrl.base.ammo = levelCtrl.numMissiles;
        hornetCtrl = new HornetCtrl(contxt, levelCtrl.numHornets);
        powerUpCtrl = new PowerUpCtrl(contxt);
        numPowerup = levelCtrl.numPowerups;
        hornetCtrl.hornetsToSpawn = levelCtrl.numHornets;
        hornetsDestroyed = 0;

    }

    // When we start the thread with:
    // mGameThread.start();
    // the run method is continuously called by Android
    // because we implemented the Runnable interface
    // Calling mGameThread.join();
    // will stop the thread
    @Override
    public void run() {
        // mPlaying gives us finer control
        // rather than just relying on the calls to run
        // mPlaying must be true AND
        // the thread running for the main loop to execute
        while (mPlaying) {

            // What time is it now at the start of the loop?
            long frameStartTime = System.currentTimeMillis();

            // Provided the game isn't paused call the update method

            // The movement has been handled and collisions
            // detected now we can draw the scene.
            if (state == 1) {
                update();
                detectCollisions();
            }
            draw();

            // How long did this frame/loop take?
            // Store the answer in timeThisFrame
            long timeThisFrame = System.currentTimeMillis() - frameStartTime;

            // Make sure timeThisFrame is at least 1 millisecond
            // because accidentally dividing by zero crashes the game
            if (timeThisFrame > 0) {
                // Store the current frame rate in mFPS
                // ready to pass to the update methods of
                // mBat and mBall next frame/loop
                mFPS = MILLIS_IN_SECOND / timeThisFrame;
            }

        }

    }

    private void update() {
        // Call all controller update functions
        if (hornetCtrl != null && baseCtrl != null && powerUpCtrl != null) {
                hornetCtrl.update(mFPS, 1, cowsCtrl, mScreenX);
                baseCtrl.update(mFPS);
                powerUpCtrl.update(mFPS, 1, mScreenX, mScreenY);
                if(cowsCtrl.getCowsAlive() == 0){
                    levelCtrl = new LevelCtrl();
                    cowsCtrl = new CowsCtrl(mScreenY, contxt);
                    menuPlayer.start();
                    state = 0;
                }
                else if (hornetCtrl.hornetsToSpawn == 0 && hornetCtrl.hornets.size() == 0) {
                    levelCtrl.nextLevel();
                    baseCtrl.base.missiles = new ArrayList<>();
                    menuPlayer.start();
                    state = 2;
                }
        }
    }

    private void detectCollisions() {
        // Has the missile hit the hornets or power ups?
        for (int i = 0; i < baseCtrl.base.missiles.size(); i++) {
            if (baseCtrl.base.missiles.get(i).exploding) {
                for (int j = 0; j < hornetCtrl.hornets.size(); j++) {
                    checkCollision(hornetCtrl.hornets.get(j), baseCtrl.base.missiles.get(i));
                }
                for (int k = 0; k < powerUpCtrl.powerUps.size(); k++) {
                    checkCollision(powerUpCtrl.powerUps.get(k), baseCtrl.base.missiles.get(i));
                }
            }
        }
    }

    private void checkCollision(Hornets hornet, Missile missile) {
        float hX = hornet.xPosition;
        float hY = hornet.yPosition;
        if (hY < missile.explodeRect.top && hY > missile.explodeRect.bottom
                && hX > missile.explodeRect.left && hX < missile.explodeRect.right) {
            hornetCtrl.hornets.remove(hornet);
            killedHornet = true;
            score = score + 10;
        }
    }

    private void checkCollision(PowerUp powerUp, Missile missile) {
        int pX = powerUp.xPosition;
        int pY = powerUp.yPosition;
        if (pY < missile.explodeRect.top && pY > missile.explodeRect.bottom
                && pX > missile.explodeRect.left && pX < missile.explodeRect.right) {
            powerUpCtrl.powerUps.remove(powerUp);
            baseCtrl.base.ammo = baseCtrl.base.ammo + 4;
            killedPowerup = true;
        }
    }

    // Draw the game objects and the HUD
    void draw() {
        if (mOurHolder.getSurface().isValid()) {
            // Lock the canvas (graphics memory) ready to draw
            mCanvas = mOurHolder.lockCanvas();

            if (state == 0) {
                mainMen.draw(mCanvas, mPaint);
                // Fill the screen with a solid color
                //mCanvas.drawColor(Color.argb(255, 26, 128, 182));
                /*
                backgrnd.draw(mCanvas, mPaint);

                mPaint.setColor(Color.argb
                        (255, 255, 255, 255));

                // Choose the font size
                mPaint.setTextSize(mFontSize);

                mCanvas.drawText("DADS", 500, 500, mPaint);
                mCanvas.drawText("Davis Aerial Defense System!", 500, 800, mPaint);
                */
                mOurHolder.unlockCanvasAndPost(mCanvas);

            }
            else if (state == 2) {
                backgrnd.draw(mCanvas, mPaint);

                mPaint.setColor(Color.argb
                        (255, 255, 255, 255));
                // Choose the font size
                mPaint.setTextSize(mFontSize);

                mCanvas.drawText("Level " + levelCtrl.level + " completed!", mScreenX/4,
                        mScreenY/2, mPaint);

                mCanvas.drawText("Score: " + score + " + " + cowsCtrl.getCowsAlive()*100 + " = " +
                Integer.toString(score + cowsCtrl.getCowsAlive()*100) + "!", mScreenX/4, mScreenY/2+300, mPaint);

                mOurHolder.unlockCanvasAndPost(mCanvas);
            }
            else {

                // Fill the screen with a solid color
                //mCanvas.drawColor(Color.argb(255, 26, 128, 182));
                backgrnd.draw(mCanvas, mPaint);

                // Choose a color to paint with
                mPaint.setColor(Color.argb
                        (255, 255, 255, 255));

                // Call all controllers draw functions
                if (cowsCtrl != null && baseCtrl != null && hornetCtrl != null && powerUpCtrl != null) {
                    cowsCtrl.draw(mCanvas, mPaint);
                    baseCtrl.draw(mCanvas, mPaint);
                    hornetCtrl.draw(mCanvas, mPaint);
                    powerUpCtrl.draw(mCanvas, mPaint);
                }

                // Reset Color to White
                mPaint.setColor(Color.argb
                        (255, 255, 255, 255));

                // Choose the font size
                mPaint.setTextSize(mFontSize);

                // Draw the HUD
                mCanvas.drawText("Score: " + score, mFontMargin, mFontSize, mPaint);
                mCanvas.drawText("Missiles: " + baseCtrl.base.ammo, mFontMargin + 500, mFontSize, mPaint);
                mCanvas.drawText("Hornets Left: " + hornetCtrl.hornetsToSpawn, mFontMargin + 1000, mFontSize, mPaint); //hornetCtrl.hornets.size()

                if (DEBUGGING) {
                    printDebuggingText();
                }
                // Display the drawing on screen
                // unlockCanvasAndPost is a method of SurfaceView
                mOurHolder.unlockCanvasAndPost(mCanvas);
            }
        }

    }

    // Handle all the screen touches
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        // This switch block replaces the
        // if statement from the Sub Hunter game
        switch (motionEvent.getAction() &
                MotionEvent.ACTION_MASK) {

            // The player has put their finger on the screen
            case MotionEvent.ACTION_DOWN:
                if (state == 1) {
                    baseCtrl.base.fire((int)motionEvent.getX(), (int)motionEvent.getY());
                }
                if (state == 0) {
                    state = 1;
                    score = 0;
                    startNewGame();
                    menuPlayer.pause();
                    startPlayer.start();
                    break;
                }
                if (state == 2) {
                    state = 1;

                    score = score + 100*cowsCtrl.getCowsAlive();
                    startNewGame();
                    menuPlayer.pause();
                    startPlayer.start();
                }

                // If the game was paused unpause
                //mPaused = false;

                //Where did the touch happen? Where it happens is where the missile will explode
                // use motionEvent.getX() + motionEvent.getX() to set where you want the missiles
                // go to

                //base.fire(motionEvent.getX(), motionEvent.getY());


                break;

            // The player lifted their finger
            // from anywhere on screen.
            // It is possible to create bugs by using
            // multiple fingers. We need to deal with this. I suggest we just stop and ignore.
            // Maybe use the first finger touch....
        }
        return true;
    }

    private void printDebuggingText(){
        int debugSize = mFontSize / 2;
        int debugStart = 150;
        mPaint.setTextSize(debugSize);
        mCanvas.drawText("FPS: " + mFPS ,
                10, debugStart + debugSize, mPaint);

    }

    // This method is called by PongActivity
    // when the player quits the game
    public void pause() {

        // Set mPlaying to false
        // Stopping the thread isn't
        // always instant
        mPlaying = false;
        try {
            // Stop the thread
            mGameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }


    // This method is called by MainActivity
    // when the player starts the game
    public void resume() {
        mPlaying = true;
        // Initialize the instance of Thread
        mGameThread = new Thread(this);

        // Start the thread
        mGameThread.start();
    }
}
