# Design Final
### Names - Jason Qiu jq39 Franklin Wu fyw4 Ann-Shine Wu aw378 Jack Ellwood jce22 Eddie Kong dk255

## Team Roles and Responsibilities

Jason - Implemented all Bloon related classes, APIs, and design patterns. I designed the APIs for all game pieces
and bloons. I used record class and property files to represent all bloon types in the game. I also
created the ability to make special bloons, made the regen and camo special bloons, and allowed users to input
special bloons by creating a bloon reader key and helping write part of the bloon reader class to read in
special bloons. I created the design patterns used by my teammates, namely the collection and iterator
classes and APIs in addition to the factory classes. Finally, I helped write part of the controller class
when connecting the backend to the front end.

Franklin - Implemented Reader class + its corresponding hierarchy; LayoutBlock, Layout in backend,
then moved to front-end. Created Controller class with Jack and Jason via pair-programming,
created GameEngine class, visualized bloon movement on front end and connected it back-end data 
structures, implemented multiple game modes and Bank in GameEngine.   

Ann-Shine Wu - Implemented all Towers (5 types of single shot towers, 2 spread shot towers) + corresponding hierarchy + creating 4 different Shooting Choices + Upgrades for Range and Shooting Rest Rate, all Road Items (3 types), all projectiles (4 types), set up Bank, as well constructed APIs for Tower, RoadItems, Bank. Implemented front-end interactions with projectiles, road items, and bloons to detect any collisions and animate popped bloons. Created PropertyFileValidator to validate property files, RoadItemValueReader to read in values for road items, RoundBonusReader to read in various formats of round bonuses, TowerValueReader to read in buy and sell values for towers.

Eddie - Implemented in-game buttons, as well as their functionalities. Implemented player 
GUI side menu for towers, implemented individual submenus for each individual tower + provided
description of each tower, implemented drag + drop of towers onto screen.

Jack - Front-end visualization, particularly menus and level layouts.


## Design goals

#### What Features are Easy to Add
* Adding new bloons - bloon types can be easily added in through the Bloons property file. To add
a new bloon, 
* Adding new special bloons - special bloon types can be easily added in by creating a class that extends
the special bloon class and is denoted by a symbol that is represented in the Bloon Reader Key
property file. 
* Adding new towers
* Adding new buttons
* levels 
* bloonwaves
* languages - add the corresponding properties files in the languages folder
* styles - add a new css document in the stylesheets folder
* images
* numeric values related to game mechanics

All explanations for how to implement these features can be found below.

## High-level Design

#### Core Classes
Controller acts as the bridge between bloons application (frontend/view) and game engine (backend/model).
APIs related to bloons, towers, layout act as a way to access the connections to the backend.
Game engine runs the backend of the game and initializes all the different game pieces, runs the logic
for the game, and connects the logic to the controller.

## Assumptions that Affect the Design

#### Features Affected by Assumptions
* In order to read in different layouts, there are set symbols denoting layout csv files that correspond
to directions, empty spaces, starting blocks, and ending blocks.
* There always has to be a dead bloon in the Bloons property file and the bloon types listed have to
be denoted in the order key and the bloons type should include its next spawned bloon.
* In the tower menu, we assume that there are only 2 possible upgrades.

## Significant Differences between Original Plan and Final Design
* Originally, we had different APIs corresponding to all the different game pieces, but in the final 
design, we had a GamePiece API that acted as the API for all pieces because they all require similar
public methods.

## New Features HowTo

#### Easy to Add Features
* Adding new bloons 
    * To add a new bloon, you have to add the name to the Order found within Bloon property file. Additionally, you have to add the bloon as a key and write in its Red Bloon Equivalent (RBE strength),its relative speed, its next bloon spawn after killed, and the number of bloons to be spawned after killed. The order has to make sense or else the program will throw an error. Finally, you have to add in an image for the bloon within Bloon images property file.
* Adding new special bloons
    * To add a special bloon, you have to create a class that extends SpecialBloon class. This class when extended, will ensure your special bloon will be incorporated within the game. Then, you can override any methods needed in the bloons class to make the special bloon functionality. Finally, you should add in special bloon images for all bloon colors within Bloon images properties file.
* Adding new towers
    * To add new types towers, you need to add to the TowerType enum and fill out it's radius, shooting speed, shooting rest rate, and whether it is a single shot tower. Then, you can create the functionalities of the tower by extending the Tower abstract class and only override methods you wish to update + override the getTowerType method to return your new enum. To add the tower in the front end, you would need to add corresponding images to the property files.
* Adding new buttons
    * If there are additional weapon types (i.e additional towers and road items), to add their button, the only extra data needed by the program is a image for the tower in-game and an image for the tower in the button, and then the button will be created accordingly and all other buttons will be repositioned to accomodate the new button.
* levels/bloonwaves
    * To add a new level, create a new level.csv file in the layouts folder in the data folder. Follow the conventions of designing a bloon path (using 0 for non-path blocks, arrows for path blocks to indicate direction). Note that the game will not work as intended if the path is not constructed so that a bloon will reacn the end of the level. Also note that the level must have a start and end block indicated by * and @, respectively. Then, add the corresponding bloon wave in the bloon_waves file. The two files must be named identically and the new bloon wave should be written according to convention as well, using the appropriate numbers to represent bloons and using ='s to  divide rounds.
* languages
* styles
* levels 
* bloonwaves
* languages - add a new folder inside of the languages folder in visualization.resources and add the 4 necessary properties files for that language.  Also make sure to add the language
to the languageList properties file.  Doing this will successfully implement the new language and allow you to select it on the start screen.
* styles - just add a new css file in the stylesheets folder in data and the new style will be immediately selectable on startup
* images
    * To add a new image, put the image into the appropriate gamePhotos subdirectory. Then, in the associated properties file, change the image directory to the new image. 
* numeric values related to game mechanics

#### Other Features not yet Done
* an option to change the pictures in the game
* reading in different CSV formats
* window resizing
* restart game button
* cheat keys
