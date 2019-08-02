package com.example.misslecommand;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.InputStream;
import java.util.*;

class MissileCommand extends SurfaceView implements Runnable{

    private final boolean DEBUGGING = false;        // Are we debugging?

    // These objects are needed to do the drawing
    private SurfaceHolder mOurHolder;
    private Canvas mCanvas;
    private Paint mPaint;

    private long mFPS;                              // How many frames per second did we get?
    private final int MILLIS_IN_SECOND = 1000;      // The number of milliseconds in a second

    // Holds the resolution of the screen
    private int mScreenX;
    private int mScreenY;
    private int mFontSize;
    private int mFontMargin;

    // The game objects controllers and other functionality classes
    private MainMenu mainMen;
    private Background backgrnd;
    private BaseCtrl baseCtrl;
    private CowsCtrl cowsCtrl;
    private HornetCtrl hornetCtrl;
    private PowerUpCtrl powerUpCtrl;
    private LevelCtrl levelCtrl;
    private Pause pause;
    private Sound sound;
    private int highScore = 0;


    /*  This variable is used to determine the current game state, which will mostly differ in the fact
        that update is only called in "in game" state, and it also determines what gets drawn and what
        happens when the player taps the screen.
        We know this isn't the optimal way to do game state, as mentioned by the professor in canvas comments,
        but we had to strike a balance between getting the game done in time and using best practices,
        and ultimately we went with the quick and dirty method to get done in time. If we had more time,
        we would correct this and other bad choices we made for the sake of time.
    */
    private int state;  // 0 = main menu, 1 = in game, 2 = in between levels, 3 = game over
    private int score;
    private boolean scoreAdjusted = false;

    private Thread mGameThread = null;      // Main Game thread

    private volatile boolean mPlaying;      // This volatile variable can be accessed from inside and outside the thread
    Context contxt;

    private int numPowerup;
    private int hornetsDestroyed;
    private boolean killedHornet = false;
    private boolean killedPowerup = false;

    private InputStream inputStream = getResources().openRawResource(R.raw.leaderboards);
    private CSVFileCtrl csvFile = new CSVFileCtrl(inputStream);
    private List<String> scoreList = csvFile.read();


    public SharedPreferences sharedpreferences;     // Using SharedPreferences to read and write data to an XML file for the leaderboards.

    /*
        Arbitrary scores to use in the SharedPreferences XML file
     */
    private int jackRScore = 9530;
    private int jackAScore = 1500;
    private int johnScore = 100;
    private int shayanSore = 2000;

    /*
        String that is inputted into the SharedPreferences XML file
     */
    private String jackHS = "JackR: " + jackRScore;
    private String Shayan = "Shayan: " + shayanSore;
    private String JackA = "JackA " + jackAScore;
    private String John = "John " + johnScore;


    public MissileCommand(Context context, int x, int y) {
        super(context);

        score = 0;
        state = 0;

        mScreenX = x;
        mScreenY = y;

        mFontSize = mScreenX / 20;      // Font is 5% (1/20th) of screen width
        mFontMargin = mScreenX / 75;    // Margin is 1.5% (1/75th) of screen width

        mOurHolder = getHolder();       // Initialize the objects ready for drawing with getHolder which is a method of SurfaceView
        mPaint = new Paint();

        contxt = context;

        /*
            Use SharedPreferences to store arbitrary leaderboards data of our 4 group members
         */
        sharedpreferences = contxt.getSharedPreferences("leaderboards", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("HS1", jackHS);
        editor.putString("HS2", Shayan);
        editor.putString("HS3", JackA);
        editor.putString("HS4", John);
        editor.commit();

        // Initializes the objects that we will be working with
        levelCtrl = new LevelCtrl();
        sound = new Sound(context, levelCtrl);
        mainMen = new MainMenu(mScreenX, mScreenY, context);
        backgrnd = new Background(mScreenX, mScreenY, context);
        cowsCtrl = new CowsCtrl(mScreenY, context, sound);
        baseCtrl = new BaseCtrl(mScreenX/2, mScreenY, context, sound);
        pause = new Pause(mScreenX, mScreenY, context);

        draw(); // since state is set to 0, draw will draw the main menu screen when the game is first opened.
    }

    /*
        This function is called each time a new level is started, it restocks the base with missiles,
        resets the hornet and power up controllers, and starts playing the in-game background music from
        the beginning.
    */
    private void startNewGame(){
        scoreAdjusted = false;
        baseCtrl.base.ammo = levelCtrl.numMissiles;
        hornetCtrl = new HornetCtrl(contxt, levelCtrl.numHornets);
        powerUpCtrl = new PowerUpCtrl(contxt);
        numPowerup = levelCtrl.numPowerups;
        hornetCtrl.hornetsToSpawn = levelCtrl.numHornets;
        hornetsDestroyed = 0;
        sound.background.seekTo(0);
        sound.background2.seekTo(0);
        sound.play(sound.background);
    }

    /*
        This is based off of the old pong run() function, but we made it so that update() is only called
        when state == 1, along with collision detection, but draw() will be called no matter what.
        This function is essentially the main game loop since it calls the main three functions which
        control the flow of the game.
    */
    @Override
    public void run() {
        // mPlaying gives us finer control rather than just relying on the calls to run
        // mPlaying must be true AND the thread running for the main loop to execute
        while (mPlaying) {

            long frameStartTime = System.currentTimeMillis();   // What time is it now at the start of the loop?

            if (state == 1) {   // Is in game state, update() every frame, and detect collisions between objects in the game
                update();
                detectCollisions();
            }
            draw();
            long timeThisFrame = System.currentTimeMillis() - frameStartTime;   // Store how long the frame/loop take

            // Make sure timeThisFrame is at least 1 millisecond because accidentally dividing by zero crashes the game
            if (timeThisFrame > 0) {
                mFPS = MILLIS_IN_SECOND / timeThisFrame;    // Store the current frame rate in mFPS so it is ready to be used in the next frame/loop
            }
        }
    }

    /*
        This function calls the update() functions inside hornet, base, and powerUp controllers, and
        also detects when a round has ended, or the player gets a game over.
     */
    private void update() {
        // Call all controller update functions
        if (hornetCtrl != null && baseCtrl != null && powerUpCtrl != null) {
                hornetCtrl.update(mFPS, levelCtrl.level, cowsCtrl, mScreenX);
                baseCtrl.update(mFPS);
                powerUpCtrl.update(mFPS, 1, mScreenX, mScreenY);

                // Next Level
                if(cowsCtrl.getCowsAlive() == 0){
                    cowsCtrl = new CowsCtrl(mScreenY, contxt, sound);
                    baseCtrl.base.missiles = new ArrayList<>();
                    sound.gameOver();
                    sound.pause(sound.background);
                    state = 3;
                }

                // Game Over
                else if (hornetCtrl.hornetsToSpawn == 0 && hornetCtrl.hornets.size() == 0) {
                    levelCtrl.nextLevel();
                    baseCtrl.base.missiles = new ArrayList<>();
                    sound.play(sound.menu);
                    sound.pause(sound.background);
                    state = 2;
                }
        }
    }

    /*
        This is the function that requires the most amount of optimization if we had more time to do so.
        It contains a nested for loop that loops through each exploding missile, then through each
        powerUp and hornet to call the appropriate checkCollision function with the two objects.
    */
    private void detectCollisions() {
        // Has the missile hit the hornets or power ups?
        for (int i = 0; i < baseCtrl.base.missiles.size(); i++) {
            if (baseCtrl.base.missiles.get(i).exploding) {
                for (int k = 0; k < powerUpCtrl.powerUps.size(); k++) {
                    checkCollision(powerUpCtrl.powerUps.get(k), baseCtrl.base.missiles.get(i));
                }
                for (int j = 0; j < hornetCtrl.hornets.size(); j++) {
                    checkCollision(hornetCtrl.hornets.get(j), baseCtrl.base.missiles.get(i));
                }
            }
        }
    }

    /*
        This function takes a hornet and a missile and checks if they have collided by calculating the
        distance from the center of the explosion and the hornet. It will then remove the hornet, add
        to score, and play the sound effect.
    */
    private void checkCollision(Hornets hornet, Missile missile) {

        float dX = Math.abs(hornet.xPosition - missile.xCenter);
        float dY = Math.abs(hornet.yPosition - missile.yCenter);

        float dist = (float)Math.sqrt(dX*dX + dY*dY);

        if ( dist-45 <= missile.radius){
            hornetCtrl.hornets.remove(hornet);
            killedHornet = true;
            score = score + 10;
            sound.squish();
        }
    }

    // Same as above but checks collision between powerUp and a missile.
    private void checkCollision(PowerUp powerUp, Missile missile) {
        float dX = Math.abs(powerUp.xPosition - missile.xCenter);
        float dY = Math.abs(powerUp.yPosition - missile.yCenter);

        float dist = (float)Math.sqrt(dX*dX + dY*dY);

        if ( dist-35 <= missile.radius){
            powerUpCtrl.powerUps.remove(powerUp);
            baseCtrl.base.ammo = baseCtrl.base.ammo + 4;
            sound.ammo();
        }
    }

    // Draw the game objects and the HUD
    void draw() {
        if (mOurHolder.getSurface().isValid()) {
            mCanvas = mOurHolder.lockCanvas();      // Lock the canvas (graphics memory) ready to draw
            mPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Bangers-Regular.ttf"));

            if (state == 0) {   // Handles the Main Menu screen
                mainMen.draw(mCanvas, mPaint);
                mOurHolder.unlockCanvasAndPost(mCanvas);
            } else if (state == 2) {    // In between levels screen
                backgrnd.draw(mCanvas, mPaint);
                mPaint.setColor(Color.argb(255, 212,0,0));
                mPaint.setTextSize(mFontSize+80);   // Choose the font size
                mCanvas.drawText("Level " + (levelCtrl.level-1) + " completed!", mScreenX/4,mScreenY/2 - 100, mPaint);
                mPaint.setTextSize(mFontSize-20);

                mCanvas.drawText(" Previous Score:              " + score, mScreenX/2-600, mScreenY/2+50, mPaint);
                mCanvas.drawText( "Cows          " + cowsCtrl.getCowsAlive()+ "x100            +" + cowsCtrl.getCowsAlive()*100, mScreenX/2-600, mScreenY/2+160, mPaint);
                mCanvas.drawText( "Missiles    " + baseCtrl.base.ammo + "x10              +" + baseCtrl.base.ammo*10, mScreenX/2-600, mScreenY/2+260, mPaint);
                mCanvas.drawText( "Total Score                    " + Integer.toString(score + cowsCtrl.getCowsAlive()*100 + baseCtrl.base.ammo*10) + "!", mScreenX/2-600, mScreenY/2+360, mPaint);

                mOurHolder.unlockCanvasAndPost(mCanvas);
            } else if (state == 3){     // Game Over Screen
                backgrnd.drawGameOver(mCanvas, mPaint);
                if (score > highScore){
                    highScore = score;
                    saveScore();
                }
                mPaint.setColor(Color.argb(255, 212,175,55));

                mPaint.setTextSize(mFontSize);  // Choose the font size
                mCanvas.drawText("Level " + (levelCtrl.level-1) + " Failed, Your Cows Are Dead", mScreenX/6,mScreenY/2+125, mPaint);
                mCanvas.drawText("Score: " + score + ". Tap anywhere to try again.", mScreenX/8, mScreenY/2+400, mPaint);
                mCanvas.drawText("Your High Score: " + highScore, mScreenX/2, mScreenY/8, mPaint);
                mCanvas.drawText("Top Scorers", mScreenX/10-100, mScreenY/8, mPaint);

                // Use temp scores to be able to print out the leaderboards correctly
                int tempScore = highScore;
                int temp1 = jackRScore;
                int temp2 = shayanSore;
                int temp3 = jackAScore;
                int hsNum = 1;

                // Prints out the leaderboards in the loop
                for(int i = 0; i < 4; i++){
                    if(tempScore < temp1){
                        mCanvas.drawText(sharedpreferences.getString("HS"+hsNum, "null"), mScreenX/10-100, mScreenY/8+((i+1)*115), mPaint);
                        temp1 = 0;
                        hsNum++;
                    } else if(tempScore < temp2){
                        mCanvas.drawText(sharedpreferences.getString("HS"+hsNum, "null"), mScreenX/10-100, mScreenY/8+((i+1)*115), mPaint);
                        temp2 = 0;
                        hsNum++;
                    } else if(tempScore < temp3){
                        mCanvas.drawText(sharedpreferences.getString("HS"+hsNum, "null"), mScreenX/10-100, mScreenY/8+((i+1)*115), mPaint);
                        temp3 = 0;
                        hsNum++;
                    } else{
                        mCanvas.drawText(sharedpreferences.getString("Guest", "null"), mScreenX/10-100, mScreenY/8+((i+1)*115), mPaint);
                        tempScore = 0;
                    }
                }
                mOurHolder.unlockCanvasAndPost(mCanvas);
            } else {
                backgrnd.draw(mCanvas, mPaint);     // Fill the screen with a solid color
                mPaint.setColor(Color.argb(255, 212,175,55));   // Choose a color to paint with

                // Call all controllers draw functions
                if (cowsCtrl != null && baseCtrl != null && hornetCtrl != null && powerUpCtrl != null) {
                    cowsCtrl.draw(mCanvas, mPaint);
                    baseCtrl.draw(mCanvas, mPaint);
                    hornetCtrl.draw(mCanvas, mPaint);
                    powerUpCtrl.draw(mCanvas, mPaint);
                    pause.draw(mCanvas, mPaint, state);
                }

                mPaint.setColor(Color.argb(255, 212,175,55));   // Reset Color to White
                mPaint.setTextSize(mFontSize);  // Choose the font size

                mCanvas.drawText("Score: " + score, mFontMargin, mFontSize, mPaint);    // Draw the HUD
                mCanvas.drawText("Missiles: " + baseCtrl.base.ammo, mScreenX- 800, mFontSize, mPaint);

                if (DEBUGGING) {
                    printDebuggingText();
                }
                mOurHolder.unlockCanvasAndPost(mCanvas);    // Display the drawing on screen unlockCanvasAndPost is a method of SurfaceView
            }
        }
    }

    // Handle all the screen touches
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        // This switch block replaces the if statement from the Sub Hunter game
        switch (motionEvent.getAction() &
                MotionEvent.ACTION_MASK) {

            // The player has put their finger on the screen
            case MotionEvent.ACTION_DOWN:
                if (state == 1) {
                    if (pause.mRect.contains(x,y)) {
                        state = 4;
                        sound.pause(sound.background);
                        break;
                    }
                    baseCtrl.base.fire((int)motionEvent.getX(), (int)motionEvent.getY());
                }
                if (state == 0) {
                    state = 1;
                    score = 0;
                    startNewGame();
                    sound.pause(sound.menu);
                    sound.play(sound.start);
                    break;
                }
                if (state == 2) {
                    state = 1;
                    score = score + 100*cowsCtrl.getCowsAlive() + baseCtrl.base.ammo*10;
                    for (int i = 0; i < cowsCtrl.cowNum; i++) {
                        cowsCtrl.cows[i].status = true;
                    }
                    startNewGame();
                    sound.pause(sound.menu);
                    sound.play(sound.start);
                }
                if (state == 3) {
                    restart();
                }
                if (state == 4) {
                    int choice = pause.option.touch(x,y);
                    if (choice == 1) {
                        sound.toggle();
                    }
                    else if (choice == 2) {
                        sound.pause(sound.background);
                        sound.pause(sound.background2);
                        sound.pause(sound.menu);
                        restart();
                    }
                    else if (choice == 3) {
                        state = 1;
                        sound.play(sound.background);
                        break;
                    }
                }
                break;
        }
        return true;
    }

    /*
        This function will restart the game by changing the game state and will set all object states to their initial states
        that they need to be in at the start of the game
     */
    private void restart() {
        state = 0;
        levelCtrl = new LevelCtrl();
        sound = new Sound(contxt, levelCtrl);
        mainMen = new MainMenu(mScreenX, mScreenY, contxt);
        backgrnd = new Background(mScreenX, mScreenY, contxt);
        cowsCtrl = new CowsCtrl(mScreenY, contxt, sound);
        baseCtrl = new BaseCtrl(mScreenX/2, mScreenY, contxt, sound);
        pause = new Pause(mScreenX, mScreenY, contxt);
    }

    private void printDebuggingText(){
        int debugSize = mFontSize / 2;
        int debugStart = 150;
        mPaint.setTextSize(debugSize);
        mCanvas.drawText("FPS: " + mFPS ,
                10, debugStart + debugSize, mPaint);
    }

    // This method is called by MainActivity when the player pauses the game
    public void pause() {
        // Stopping the thread isn't always instant
        mPlaying = false;
        try {
            mGameThread.join();     // Stop the thread
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }

    // This method is called by MainActivity when the player continues the game
    public void resume() {
        mPlaying = true;
        mGameThread = new Thread(this); // Initialize the instance of Thread
        mGameThread.start();    // Start the thread
    }

    /*
        This function saves the score of the Guest (the player playing the game) to the sharedPreferences XML file
     */
    public void saveScore(){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String guest = "Guest: " + highScore;
        editor.putString("Guest",guest);
        editor.commit();
    }
}
