## Description

When a camo or regen bloon spawns (special bloons), it does not disappear when killed. Instead,
it stays on the screen and an error is thrown because it is not removed when it should have been. 

## Expected Behavior

The expected behavior is for the special bloon to disappear from the game when killed.

## Current Behavior

The bloon stays on the screen and freezes the game. 

## Steps to Reproduce

 1. Put a special bloon in the level csv file.
 2. Start the game, pick a game mode and level corresponding to the csv file so that the 
 special bloon runs through the game. Place a tower down that can shoot at the bloon and 
 shoots enough times to kill it.

## Failure Logs

Exception in thread "JavaFX Application Thread" java.util.MissingResourceException: Can't find resource for bundle java.util.PropertyResourceBundle, key DEAD
	at java.base/java.util.ResourceBundle.getObject(ResourceBundle.java:564)
	at java.base/java.util.ResourceBundle.getString(ResourceBundle.java:521)
	at ooga.visualization.nodes.BloonNode.findImage(BloonNode.java:33)
	at ooga.visualization.nodes.BloonNode.<init>(BloonNode.java:22)
	at ooga.visualization.animationhandlers.AnimationHandler.addBloonstoGame(AnimationHandler.java:82)
	at ooga.visualization.animationhandlers.AnimationHandler.animateBloons(AnimationHandler.java:116)
	at ooga.visualization.animationhandlers.AnimationHandler.animate(AnimationHandler.java:109)
	at ooga.controller.Controller.step(Controller.java:212)
	at ooga.controller.Controller.lambda$startLevel$2(Controller.java:99)
	at javafx.graphics/com.sun.scenario.animation.shared.TimelineClipCore.visitKeyFrame(TimelineClipCore.java:239)
	at javafx.graphics/com.sun.scenario.animation.shared.TimelineClipCore.playTo(TimelineClipCore.java:180)
	at javafx.graphics/javafx.animation.Timeline.doPlayTo(Timeline.java:175)
	at javafx.graphics/javafx.animation.AnimationAccessorImpl.playTo(AnimationAccessorImpl.java:39)
	at javafx.graphics/com.sun.scenario.animation.shared.InfiniteClipEnvelope.timePulse(InfiniteClipEnvelope.java:108)
	at javafx.graphics/javafx.animation.Animation.doTimePulse(Animation.java:1101)
	at javafx.graphics/javafx.animation.Animation$1.lambda$timePulse$0(Animation.java:186)
	at java.base/java.security.AccessController.doPrivileged(AccessController.java:391)
	at javafx.graphics/javafx.animation.Animation$1.timePulse(Animation.java:185)
	at javafx.graphics/com.sun.scenario.animation.AbstractMasterTimer.timePulseImpl(AbstractMasterTimer.java:344)
	at javafx.graphics/com.sun.scenario.animation.AbstractMasterTimer$MainLoop.run(AbstractMasterTimer.java:267)
	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumToolkit.pulse(QuantumToolkit.java:559)
	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumToolkit.pulse(QuantumToolkit.java:543)
	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumToolkit.pulseFromQueue(QuantumToolkit.java:536)
	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumToolkit.lambda$runToolkit$11(QuantumToolkit.java:342)
	at javafx.graphics/com.sun.glass.ui.InvokeLaterDispatcher$Future.run(InvokeLaterDispatcher.java:96)

## Hypothesis for Fixing the Bug

The test I can make to verify this bug is to check if the special bloon is dead when shot at and the
dead bloon it produces is dead. The code I believe I can change to remedy this issue is the code
where I check if the bloon is dead in the bloon class.