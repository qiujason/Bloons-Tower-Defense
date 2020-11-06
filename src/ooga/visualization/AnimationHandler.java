package ooga.visualization;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class AnimationHandler {
  public static final double FRAMES_PER_SECOND = 60;
  public static final double ANIMATION_DELAY = 1 / FRAMES_PER_SECOND;

  private Timeline myAnimation;
  private Group myLevelLayout;
  private double myStartingX;
  private double myStartingY;
  private double myBlockSize;
  private Circle myTestCircle;

  public AnimationHandler (Group levelLayout, double startingX, double startingY, double blockSize){
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame movement = new KeyFrame(Duration.seconds(ANIMATION_DELAY), e -> animate());
    myAnimation.getKeyFrames().add(movement);
    myAnimation.play();

    myLevelLayout = levelLayout;
    myStartingX = startingX;
    myStartingY = startingY;
    myBlockSize = blockSize;

    myTestCircle = new Circle(myStartingX, myStartingY, myBlockSize / 2, Color.RED);
    myTestCircle.setId("TestCircle");
    myLevelLayout.getChildren().add(myTestCircle);
  }

  private void animate() {
    animateBloons();
    animateTowers();
  }

  private void animateBloons() {
    myTestCircle.setCenterX(myTestCircle.getCenterX() + 2);
  }

  private void animateTowers() {

  }

  public Timeline getAnimation() {
    return myAnimation;
  }

}
