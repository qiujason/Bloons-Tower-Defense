# Use Cases

## Jason

### Starting the game
* Player starts the game by running the main method
* Precondition: None
* Primary Actor: Player
* Triggers: Click the run button

### Place monkey in valid spot
* Player places his monkey down in a valid space, which then puts a monkey in that position as long as the player can afford the monkey’s price.
* Precondition: JavaFx scene has loaded in and the game is visible to the player
* Primary Actor: Player
* Triggers: Click on monkey and click on valid area on map

### Place monkey in invalid spot
* Player tries to place a monkey in an invalid space such as an obstacle or on the path, but the monkey is not put in that position.
* Precondition: JavaFx scene has loaded in and the game is visible to the player
* Primary Actor: Player
* Triggers: Click on monkey and click on invalid area on map

### Run next level
* Player clicks the play button to start the next level.
* Precondition: The game level is not active
* Primary Actor: Player
* Triggers: Player clicks on the play button

### Fast forward current level
* Player clicks the fast forward button to make the level and its animations speed up
* Precondition: The game level is active and not paused
* Primary Actor: Player
* Trigger: Player clicks on the fast forward button

## Annshine

### Pause current level
* Player clicks the pause button to pause the level and its animations.
* Precondition: The game level is active
* Primary Actor: Player
* Trigger: Player clicks on the pause button

### Popping a balloon increases money 
* Popping a balloon increases money in bank account by fixed amount
* Precondition: The game level is active
* Primary Actor: Game
* Trigger: Monkey pops balloon

### Open upgrade menu
* Player clicks the monkey on the game level to open a menu for upgrades
* Precondition: The monkey is on the game level
* Primary Actor: Player
* Trigger: Player clicks on the monkey on the game level

### Close upgrade menu
* Player clicks on the game level that is not a monkey
* Precondition: The upgrade menu is open
* Primary Actor: Player
* Trigger: Player clicks on the game level that is not a monkey

### Player clicks on an upgrade they can’t afford
* Player clicks on an upgrade that costs more than the amount of money they have
* Precondition: The game level is active
* Primary Actor: Player clicks on the pause button

## Jack

### Bloon reaches the end of a level
* A bloon reaches the end of the level, disappears, and decrements the player’s health
* Precondition: The game is running
* Primary actor: Bloon object/game
* Trigger: A bloon reaches the end of a level without being popped

### The player loses the game
* The player loses all of their health, resulting in a game over screen from which to restart the level, or return to the game menu
* Precondition: The game is running and a bloon reaches the end of a level
* Primary actor: Bloon/game
* Trigger: A bloon reaches the end of the level and decrements the player’s health to <= 0

### The player wins the game
* The player pops all bloons in a level, resulting in a win screen for the player to progress to the next level (or maybe save progress and return to the menu?)
* Precondition: The game is running and there are no remaining bloons on the level or waiting to be spawned
* Primary actor: Player
* Trigger: A player pops the last remaining bloon on screen and none are waiting to be spawned.

### Monkey chooses a target and throws a dart
* A monkey chooses the closest bloon in its range (or maybe the one that’s farthest along the path) and throws a dart (running the “throw” animation)
* Precondition: The game is running, the monkey has a bloon in its range, and the monkey’s “cooldown” has expired (since the monkey should not be able to constantly throw darts)
* Primary actor: Monkey
* Trigger: Either a bloon enters the monkey’s range, or its cooldown just expired and there’s already a bloon in its range

### Load in a saved game
* A player presses the load button from the menu, loads a game, and can select from the levels they’ve completed/the current level they’re on (or maybe the most recent level should automatically start playing)
* Precondition: The player is in the main menu
* Primary actor: Player
* Trigger: The player presses the load game button, then selects a saved game

##Franklin

### Click on a tower to show its range
* A player clicks on a tower and a highlighted circle in the tower shows up, indicating the range in which it can hit balloons
* Precondition: the player is in the game (can be paused or unpaused) and a tower is on the map
* Primary actor: Player
*Trigger: The player presses on the desired tower

### Popping a higher level balloon releases lower level balloons
* When a higher level balloon is popped, it can either downgrade into a lower level balloon (i.e blue → red) or it can release a lot of lower-level balloons
* Precondition: The game is running
* Primary actor: Bloon object/game
* Trigger: A balloon is popped

### Load different versions of BTD - capped cash, unlimited waves
* When the player is in the menu, he can select different versions of the game to be played
* Precondition: In the main menu
* Primary actor: Player
* Trigger: The player clicks on a button corresponding to the game type he wants

### Loading a new level after the current one has been completed
* After the current level is completed, the next level should be able to load in and the level should start
* Precondition: game is still running
* Primary actor: game
* Trigger: A level has just been passed

### Display Round #, Amount of money, Amount of Lives remaining
* Throughout the game, the round number, money, and lives remaining should constantly be updated to display accurate figures
* Precondition: The game is running
* Primary actor: the game itself
* Trigger: A balloon has been popped, a balloon has passed through the level, or a level has been completed


##Eddie

### Click on a monkey in the menu to see a description of what it does.
* A player clicks on the monkey in the shop to see what the monkey does
* Precondition: In between the levels
* Primary actor: Player and Game
*Trigger: The player presses on the monkey icon

### Sell a monkey currently placed in map
* A player clicks on the monkey and has an option to sell
* Precondition: Monkey is already in play
* Primary actor: Player and Game
* Trigger: The player presses on the monkey and clicks cell

### Shooting rate upgrade
* A player clicks on the monkey’s upgrade menu and click shooting rate upgrade
* Precondition: Monkey is in play and the menu is opened
* Primary actor: Player and Monkey
* Trigger: The player clicks the upgrade button

### AOE upgrade
* A player clicks on the monkey’s upgrade menu and clicks on AOE upgrade which lets the monkey hit an AOE area from the first balloon it aims at
* Precondition: Monkey is in play and the menu is opened
* Primary actor: Player and Monkey
* Trigger: The player clicks the upgrade button

### Place spikes on the path of the balloons
* Player buys the spikes and places it on the path of balloons
* Precondition: Enough money and the space chosen is the path
* Primary actor: Map, Menu, Spikes/weapon
* Trigger: The player purchases and places it on a spot
