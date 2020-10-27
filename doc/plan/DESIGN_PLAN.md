### Introduction

We plan on designing a well-known classic game called Bloons Tower Defense. It is a tower defense based game, and the concept of the game is that the user tries to place down towers that shoots and pops balloons before the balloons are able to escape. Our overall design goal is to make it as easy as possible to implement new features based on the existing code framework, which is particularly important for our project, since there could potentially be a large variety of towers, balloons, levels, etc. We also would like to try to make the user interface as aesthetic and easy to use as possible in order to improve the game experience. In general, we want to make the design open to adding more features that build off of existing features (i.e new towers, new balloon types, new levels), but closed to modification. At a high level, we want to have the backend handle the rules of the game and the interactions between objects, and we want the front-end to simply be a visualization of the backend work. One note is that the front-end will have to communicate with the back end to determine the coordinates of towers and balloons so that the game can function correctly, and this can be done through APIs. The controller will take in user input (i.e drag-and-drop towers, mouse click actions, etc).

### Overview

https://app.creately.com/diagram/3aOXPhZFpez/edit

### Design Details

#### Layout

To implement the layout, we will first have a reader class that reads in the layout from a CSV file (which maps out the path the bloons will take through the level. The reader class will create a layout block object based on the csv, which is how the backend will handle the layout. There will be a Layout class that stores a List<List<LayoutBlock>> object, which will be how the backend interacts with the layout.

#### Menu/Display

The actual application will run in BloonsApplication and primarily interface with the Layout class in order to produce a display.  The game will start with a startup screen that offers users the option to start a new game, load and continue an existing one, or quit.
 
The in-game display will have buttons to allow users to place new towers, upgrade existing ones, pause, play, and speedup the level, save their current progress, and return to the main menu.  It will also display relevant information, such as money, health, score, bloons popped, and other potential stats.

The display will read then display the information from the objects created in the backend and create the physical buttons and menu pane. However, the menu’s implementations (button and in-game control functions) will be created in a separate Controller package. Communication between these will occur using the interfaces from each of the components. For example, the way the display calls on the button function after a user-input will be by using a ControllerInterface.

#### Physics

The goal for the physics of the bloon movement is that we want the movement to be as smooth as possible. Bloons should follow the track, and when there are turns, the bloon should change direction accordingly. The bloons also have to continue down a path that is labeled by the LayoutBlocks. This work can be done within a Movement class that calls a method in the Layout class that calls for each bloon to update its position accordingly. The positions are passed to the frontend via an API. 

The physics of the dart movement is such that when a dart is launched and hits a bloon, the bloon will pop. The movement class can call a shoot() method within all towers that generates a dart that moves at a velocity that will allow it to reach a targeted bloon.

The towers will also rotate based on the tower’s next target. The tower will track its next target based on a rule (such as closest bloon or first bloon) and rotate to face that bloon. This can be done in the Movement class, where a method is called such that each tower iterates through the Bloon data structure, finds a target bloon, and rotates its image so that it appears to face the target bloon.

#### Towers and Bloons

For our project, we will be dividing the framework for the program into separate view (front-end) and model (back-end) components. To model the Bloons Tower Defense, we will need to create interfaces to represent the different kinds of Towers that can be placed on the map and the different kinds of bloons. The Tower will have to be able to create darts that can be shot, and bloons will have to be able to detect darts that hit it. The bloons will also have to be able to create other kinds of bloons once it has been shot. This will be done by overriding the method popBloons() in the interface and implementing different kinds of bloon each type of bloon will create after it has been popped. For both towers and bloons, there will be a enum to represent the different variations of each tower and bloon which will be called TowerType and BloonType.

### Example Games

Although we are focusing on creating one complex game, we do plan to have different “modes” of our game. First, we would have an Easy/Medium/Hard choice for the user, which will vary by the number of lives they start off with and the amount of money per balloon pop. Second, we also plan to have two different variations of bloons tower defense besides the normal version: one where we cap the player’s money (start off with more money, but can’t earn money by popping balloons), and another where there is an unlimited amount of balloons that come in waves.

### Design Considerations

* How to get balloons to move along path
	* Choice 1: have each LayoutBlock store a dx and dy, which is then passed to the bloon
		* Pros: Very easy for balloon to know where to go
		* Cons: more responsibility falls on layout grid block, must create multiple types of blocks (i.e corner blocks, left facing and right facing, etc)
	* Choice 2: have the bloon search for the next block it should go to
		* Pros: makes layout very simple
		* Cons: probably not very efficient, needs more algorithmically challenging methods