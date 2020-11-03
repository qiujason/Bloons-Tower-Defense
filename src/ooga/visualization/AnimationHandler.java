package ooga.visualization;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class AnimationHandler {
  public static final double FRAMES_PER_SECOND = 60;
  public static final double ANIMATION_DELAY = 1 / FRAMES_PER_SECOND;

  private Timeline myAnimation;

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
