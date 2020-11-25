## Description

The feature's bug is that the regen bloon is not regenning to a higher bloon after being hit. Instead
it stays as its own bloon and even after being hit again, it still remains as that bloon.

## Expected Behavior

When shot at, the bloon should go to its lower bloon type and then regen over a period of time to its
higher bloon type. Also, when hit it should always go down to its lower bloon type and not remain
at the same bloon type.

## Current Behavior

The bloon goes to its lower bloon type once and remains there even after being hit and even after
it should be regenning. Essentially, after being shot once, it stays at that bloon type forever.

## Steps to Reproduce

 1. Put a regen bloon (*) in the level csv file.
 2. Start the game, pick a game mode and level corresponding to the csv file so that the 
 regen bloon runs through the game. Place a tower down that can shoot at the regen bloon and observe
 as the regen bloon hasa its error.

## Failure Logs

None (feature bug)

## Hypothesis for Fixing the Bug

Describe the test you think will verify this bug and the code fix you believe will solve this issue.
The test that will verify this bug is to see if the image is updating and compare it with an expected
image. The code fix that should solve this issue is to update BloonNode whenever the Bloonstype is updated.