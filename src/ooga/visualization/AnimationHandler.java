package ooga.visualization;

import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import ooga.backend.bloons.Bloons;
import ooga.backend.towers.Tower;

public class AnimationHandler {
  public static final double FRAMES_PER_SECOND = 60;
  public static final double ANIMATION_DELAY = 1 / FRAMES_PER_SECOND;

  private Timeline myAnimation;
  private List<Bloons> myBloons;
  private List<Tower> myTowers;
  private Circle myTestCircle;

  public AnimationHandler (){
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame movement = new KeyFrame(Duration.seconds(ANIMATION_DELAY), e -> animate());
    myAnimation.getKeyFrames().add(movement);
    myAnimation.play();
  }

  private void animate() {
    // TODO: Any methods related to animation

  }


}
