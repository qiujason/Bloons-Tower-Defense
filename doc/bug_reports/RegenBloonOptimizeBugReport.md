## Description

The feature's bug is that the regen bloon is fixed however it is incredibly unoptimized, even for normal bloons.

## Expected Behavior

The bloons should animate smoothly.

## Current Behavior

The animation of the bloons moving gets jittery as more bloons appear on screen and eventually the program crashes.

## Steps to Reproduce

 1. Run any game with more than 10 bloons.

## Failure Logs

None (animation bug)

## Hypothesis for Fixing the Bug

Run the same tests as the Regen Bloon bug report except overload it with a lot of bloons
and test to see that the runtime of it is less than a certain number of milliseconds.