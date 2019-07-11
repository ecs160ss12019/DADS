# CRC Cards in plain text 

# MissileCommand.java:
Description: 

* Main logic loop of the game
* Keep score of the game
* Collision detection (Detects when hornets touch missile explosion or cows, calls appropriate methods)
* Start new game
* Change rounds/levels
* Draws the initial level screen (Foreground and Background)
* Also calls other draw functions for all objects on screen, ie hornets, cows, etc
* Tracks FPS for animations

Interactions:

* Base.java
* Cows.java
* Hornets.java
* Missile.java
    
# Hornet.java
Description: 

Variables: 
* xVelocity
* yVelocity
* Image img 
* Spawn Coords (X and Y)
* Destination Coords (X and Y)
* Position (RectF)
* Width and Height
* isAlive

Methods: 
* getPosition() 
* kill()
* increaseSpeed()
* draw()

Explanation:

Hornet.java is the class that tracks the hornet stingers coming down from the sky, in place for enemy missiles in the classic game. It draws itself, kills itself when interacts with missiles or cows, and increases in speed each round.



