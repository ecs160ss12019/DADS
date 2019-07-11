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

*NOTE: Since MissileCommand.java is largely the only class that calls other classes, the CRC cards for other classes will follow a different pattern of having columns for variables and methods, and a description underneathe the table. Plus, an additional column for collaborations if present. This is to enhance readability by not having one long column for responsibility and one empty column for collaboration. Instead, to have a format of 2 equally spaced columns serving for responsibility, and not having collaboration when it is not needed.*

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


# Missile.java

Variables:

* xVelocity
* yVelocity
* xPosition
* yPosition
* xTarget
* yTarget
* missileWidth
* missileHeight
* Image img

Methods:

* getPosition()
* update()
* kill()
* status()
* draw()
* explode()
* explosionSize()

Interacts with:

* None (other classes interact with Missile, but not visa versa)

Explanation:

* Missile.java is the class that tracks the player fired missiles that explode when they reach their target position after traveling at a set velocity. The explosion changes in size over time, which explosionSize() calculates. It is also resOnce the explosion ends, it also then removes itself from the screen.

# Base.java

Variables:

* Image img
* xPosition
* yPosition
* ammoCount

Methods:

* getPosition()
* kill()
* draw()
* fire()

Interacts with:

Missile.java when fire() is called, passes origin and target coordinates and creates new instance of missile.

Explanation:

Base.java is the class that handles the player’s DADS base. The main job of Base is to create instances of Missiles that head towards the spot where the player presses the screen, and keep track of how many Missiles they have left. It also draws itself and stores its own coordinates.

# Cow.java

Variables:

* xPosition
* yPosition
* isAlive
* Cow Image

Methods:

* getPosition()
* kill()
* revive()
* draw()
* moo()

Interacts with:

None (other classes interact with Hornet, but not visa versa)

Explanation:

Hornet.java is the class that tracks the cows that the DADS must defend! The class is very simple, as the cows only have to keep track of their position, whether they have been destroyed or not, the ability to destroy them and the ability to revive them after reaching a score threshold. Also, if the player taps on them, we plan on making them moo, time allowing.

# Building.java

Variables:

* xPosition
* yPosition
* isAlive
* Building Image

Methods:

* getPosition()
* kill()
* revive()
* draw()

Interacts with:

None (other classes interact with Hornet, but not visa versa)

Explanation:

Hornet.java is the class that tracks the Davis buildings that the DADS must defend! It is essentially the same as Cow.java, but with a different image and it cannot moo().

# Powerup.java

Variables:

* Image img
* xPosition
* yPosition
* yVelocity
* String powerUpType

Methods:

* getPosition()
* kill()
* draw()
* addMissiles

Interacts with:

Base.java to call its addAmmo() function.

Explanation:

Base.java is the class that handles the player’s DADS base. The main job of Base is to create instances of Missiles that head towards the spot where the player presses the screen, and keep track of how many Missiles they have left. It also draws itself and stores its own coordinates.

# MainActivity.java

Variables:

* MissileCommand

Methods:

* createGame()
* pauseGame()
* resumeGame()

Interacts with:

MissileCommand.java to start the game.

Explanation:

MainActivity.java is responsible for creating a new instance of the game, then possibly pausing and resuming it, time allowing.

