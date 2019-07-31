package com.example.misslecommand;

public class LevelCtrl {

    public int level;
    public HornetCtrl hornets;
    public int numHornets;
    public int numMissiles;
    public int numPowerups;
    public int speedDivisor;    // Math will be calculated to take care of the speed of the falling hornets

    public LevelCtrl(){
        level = 1;
        numHornets = 10;
        numMissiles = 10;
        numPowerups = 2;
    }

    public void nextLevel(){
        level++;
        numHornets *= 1.2;
        numMissiles *= 1.1;
        if(level % 2 == 0){
            numPowerups += 1;
        }
    }

}
