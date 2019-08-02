package com.example.misslecommand;

public class LevelCtrl {

    public int level;
    public int numHornets;
    public int numMissiles;
    public int numPowerups;

    /*
        Initialize the variables in LevelCtrl
     */
    public LevelCtrl(){
        level = 1;
        numHornets = 10;
        numMissiles = 10;
        numPowerups = 2;
    }

    /*
        When the next level is reached, increment number of hornets, missiles, and powerups when the level is incremented
     */
    public void nextLevel(){
        level++;
        numHornets *= 1.2;
        numMissiles *= 1.1;
        if(level % 2 == 0){
            numPowerups += 1;
        }
    }
}
