# CRC Cards

## MissileCommand.java:
| Responsibility | Collaboration |
|---|---|
| Main logic loop of the game | Base.java
| Keep score of the game | Cows.java
| Collision detection (Detects when hornets touch missile explosion or cows, calls appropriate methods) | Hornets.java
| Start new game |  Missile.java
| Change rounds/levels | 
| Draws the initial level screen (Foreground and Background) | 
| Also calls other draw functions for all objects on screen, ie hornets, cows, etc | 
| Tracks FPS for animations |
| Spawn enemy hornets and powerups |
| Respawn cows after certain point thresholds reached

*NOTE: Since MissileCommand.java is largely the only class that calls other classes, the CRC cards for other classes will follow a different pattern of having columns for variables and methods, and a description underneathe the table. Plus, an additional column for collaborations if present. This is to enhance readability by not having one long column for responsibility and one empty column for collaboration. Instead, to have a format of 3 equally spaced columns serving for responsibility, and not having collaboration when it is not needed.*

## Hornet.java
| Variables | Methods |
| --- | --- |
| xVelocity | getPosition() |
| yVelocity | kill() |
| xSpawn | increaseSpeed() |
| ySpawn | draw() |
| xDestination | update() |
| yDestination |
| Position (RectF) |
| isAlive |

|Explanation:|
|---|
|Hornet.java is the class that tracks the hornet stingers coming down from the sky, in place for enemy missiles in the classic game. It draws itself, kills itself when interacts with missiles or cows, and increases in speed each round. Hornets will be spawned by MissileCommand.java with a randomized x position at the top of the screen, with a predetermined destination coordinate that corresponds with a cows position, dead or alive. |



