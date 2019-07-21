package com.example.misslecommand;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.*;

class MissileCommand extends SurfaceView implements Runnable{

    // Are we debugging?
    private final boolean DEBUGGING = true;

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

    // The game objects


    private BaseCtrl baseCtrl;
    private CowsCtrl cowsCtrl;
    private HornetCtrl hornetCtrl;

    private List<PowerUp> powerUps;

    // The current score and lives remaining
    private int mScore = 0;
    //private int numMissiles = 10;

    // Here is the Thread and two control variables
    private Thread mGameThread = null;

    // This volatile variable can be accessed
    // from inside and outside the thread
    private volatile boolean mPlaying;
    private boolean mPaused = true;

    // The PongGame constructor
    // Called when this line:
    // mPongGame = new PongGame(this, size.x, size.y);
    // is executed from PongActivity
    public MissileCommand(Context context, int x, int y) {
        // Super... calls the parent class
        // constructor of SurfaceView
        // provided by Android
        super(context);

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

        // Initialize the cows and base
        cowsCtrl = new CowsCtrl(mScreenY);
        baseCtrl = new BaseCtrl(mScreenX/2, mScreenY-100);

        // Everything is ready so start the game
        startNewGame();
    }

    // The player has just lost, beat the round
    // or is starting their first game
    private void startNewGame(){
        // Rest the score and the player's missiles
        mScore = 0;
        baseCtrl.base.ammo = 10;
        hornetCtrl = new HornetCtrl();
        powerUps = new ArrayList<>();
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
            update();
            detectCollisions();
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
        hornetCtrl.spawnHornets(1, cowsCtrl, mScreenX);
        hornetCtrl.updateHornets(mFPS);
        baseCtrl.update(mFPS);
        spawnPowerUps(1);
        updatePowerUps();
        //updateCows();

        //cow.update(mFPS);
        //base.update(mFPS);
        //missile.update(mFPS);
        //hornet.update(FPS);
    }

    private void detectCollisions(){
        // Has the missile hit the hornets?
        // Has the hornet stinger hit the cows

    }

    // Draw the game objects and the HUD
    void draw() {
        if (mOurHolder.getSurface().isValid()) {
            // Lock the canvas (graphics memory) ready to draw
            mCanvas = mOurHolder.lockCanvas();

            // Fill the screen with a solid color
            mCanvas.drawColor(Color.argb
                    (255, 26, 128, 182));

            // Choose a color to paint with
            mPaint.setColor(Color.argb
                    (255, 255, 255, 255));

            // Draw the cows, base, hornets, missiles
            cowsCtrl.draw(mCanvas, mPaint);
            baseCtrl.draw(mCanvas, mPaint);
            hornetCtrl.draw(mCanvas, mPaint);

            // Choose a color to paint with
            mPaint.setColor(Color.argb
                    (255, 255, 0, 0));

            for (int i = 0; i < powerUps.size(); i++) {
                mCanvas.drawRect(powerUps.get(i).mRect, mPaint);
            }

            // Choose a color to paint with
            mPaint.setColor(Color.argb
                    (255, 255, 255, 255));



            //mCanvas.drawRect(missile.getRect(), mPaint);
            //mCanvas.drawRect(hornets.getRect(), mPaint);

            // Choose the font size
            mPaint.setTextSize(mFontSize);

            // Draw the HUD
            mCanvas.drawText("Score: " + mScore +
                            "   Number of Missiles: " + baseCtrl.base.ammo,
                    mFontMargin , mFontSize, mPaint);

            mCanvas.drawText("Hornets: " + hornetCtrl.hornets.size(), mFontMargin + 1300, mFontSize, mPaint);

            if(DEBUGGING){
                printDebuggingText();
            }
            // Display the drawing on screen
            // unlockCanvasAndPost is a method of SurfaceView
            mOurHolder.unlockCanvasAndPost(mCanvas);
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
                baseCtrl.base.fire((int)motionEvent.getX(), (int)motionEvent.getY());
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





    private void spawnPowerUps(int level) {
        Random random = new Random();
        //random.nextInt(mScreenX)
        int didFire = random.nextInt(1000);
        if (didFire <= level) {
            powerUps.add(new PowerUp(random.nextInt(mScreenX-69), 0, mScreenY));
        }
    }

    private void updatePowerUps() {
        for (int i = 0; i < powerUps.size(); i++) {
            powerUps.get(i).update(mFPS);
            powerUps.get(i).kill();
            if (powerUps.get(i).status == false) {
                powerUps.remove(i);
            }
        }
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
