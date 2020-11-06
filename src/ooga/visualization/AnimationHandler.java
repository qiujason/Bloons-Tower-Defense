package ooga.visualization;

import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class AnimationHandler {

  public static final double FRAMES_PER_SECOND = 60;
  public static final double ANIMATION_DELAY = 1 / FRAMES_PER_SECOND;
  public static final double SPEED = 3;

  private Timeline myAnimation;
  private List<List<String>> myLayout;
  private Group myLevelLayout;
  private double myStartingX;
  private double myStartingY;
  private double myBlockSize;
  private Circle myTestCircle;
  private double myCircleSideX;
  private double myCircleSideY;

  public AnimationHandler(List<List<String>> layout, Group levelLayout, double startingX,
      double startingY, double blockSize) {
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame movement = new KeyFrame(Duration.seconds(ANIMATION_DELAY), e -> animate());
    myAnimation.getKeyFrames().add(movement);

    myLayout = layout;
    myLevelLayout = levelLayout;
    myStartingX = startingX;
    myStartingY = startingY;
    myBlockSize = blockSize;

    myTestCircle = new Circle(myStartingX, myStartingY, myBlockSize / 2.5, Color.RED);
    myTestCircle.setId("TestCircle");
    myLevelLayout.getChildren().add(myTestCircle);
  }

  private void animate() {
    animateBloons();
    animateTowers();
  }

  // TODO: Refactor
  private void animateBloons() {
    String currentBlockString = myLayout
        .get((int) ((myTestCircle.getCenterY() + myCircleSideY) / myBlockSize))
        .get((int) ((myTestCircle.getCenterX() + myCircleSideX) / myBlockSize));
    switch(currentBlockString) {
      case "*", ">" -> {
        myTestCircle.setCenterX(myTestCircle.getCenterX() + SPEED);
        myCircleSideX = -myBlockSize/2;
        myCircleSideY = 0;
      }
      case "<" -> {
        myTestCircle.setCenterX(myTestCircle.getCenterX() - SPEED);
        myCircleSideX = myBlockSize/2;
        myCircleSideY = 0;
      }
      case "v" -> {
        myTestCircle.setCenterY(myTestCircle.getCenterY() + SPEED);
        myCircleSideX = 0;
        myCircleSideY = -myBlockSize/2;
      }
      case "^" -> {
        myTestCircle.setCenterY(myTestCircle.getCenterY() - SPEED);
        myCircleSideX = 0;
        myCircleSideY = myBlockSize/2;
      }
      case "@" -> myLevelLayout.getChildren().remove(myTestCircle);
    }
  }

  private void animateTowers() {

  }

  public Timeline getAnimation() {
    return myAnimation;
  }

}
