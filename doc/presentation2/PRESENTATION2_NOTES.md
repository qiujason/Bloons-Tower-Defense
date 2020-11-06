## Presentation Notes

* run the program from the master branch through a planned series of steps/interactions (including
showing how bad data or interactions are handled — if any are yet)

Run program; demonstrate play, pause, speedup, slowdown; place a tower; show how placing a tower in
a bad location results in an error message.

* NOTE, as each feature is shown, someone on the team should briefly describe how work they did over
the past week relates to it

Jack - Visualizing, animating balloon
Eddie - Buttons
Franklin - Layout creation and level csv file reading
Jason - Bloons
Annshine - Towers

* show any data files that have been created and describe how they are used within your program (focus
on the file content rather than the code that uses it)

level1 csv file, blockMappings, maybe menuEnglish as well

* show a variety of JUnit or TestFX tests (including both happy and sad paths) and discuss how
comprehensively they cover the feature(s) and why you chose the values used in the tests

BloonsApplicationTest, backend test

### Then present what was learned during this Sprint and the implementation plan for the next Sprint:

* describe how much of the planned features were completed this Sprint and what helped or impeded
progress (or if the estimate was just too much)

API for Tower, Bloons, and Layout completed.  AlertHandler completed as well. Towers and Bloons
visualization and relevant GUI buttons.  Didn't quite get to physics.

* describe a specific significant event that occurred this Sprint and what was learned from it



* describe what worked regarding your teamwork and communication, what did not, and something specific
that is planned for improvement next Sprint

We adhered pretty closely to our design plan and used many APIs, making it easy for us to interface
with each other's classes.  We met multiple times throughout the week and communicated well about major
design decisions.

One thing that didn't work as well was waiting on each other's code, since we did a lot of integration to get
started this sprint.  Going forward, we can communicate deadlines better and if someone is holding someone
else up, make that piece of code/issue a priority. 

* what features are planned to be completed during the next Sprint (taking into account what was, or
was not, done this Sprint), who will work on each feature, and any concerns that may complicate the plan

More physics, shooting mechanics, and game rules/economy are some of the major features to be implemented.

Franklin: physics, shooting mechanics  
Jason: physics, shooting mechanics  
Annshine: implement economy  
Jack: front-end animations (shooting, turning monkeys)  
Eddie: rules of the game, display, click tower to show range
