package ooga.controller;

import javafx.animation.Animation.Status;
import javafx.animation.Timeline;

public class GameMenuController implements GameMenuInterface {

  private Timeline myTimeline;

  private static final double MAX_RATE = 1.5;
  private static final double MIN_RATE = 0.5;
  private static final double DEFAULT_RATE = 1.0;
  private static final double DELTA_RATE = 0.25;

  private double currentRate;

  public GameMenuController(Timeline timeline){
    myTimeline = timeline;
    currentRate = DEFAULT_RATE;
  }

  @Override
  public void play() {
    if (myTimeline.getStatus() != Status.RUNNING) {
      myTimeline.play();
    }
  }

  @Override
  public void pause() {
    if (myTimeline.getStatus() == Status.RUNNING) {
      myTimeline.pause();
    }
  }

  @Override
  public void speedUp() {
    if(currentRate < MAX_RATE){
      currentRate += DELTA_RATE;
    }
    myTimeline.setRate(currentRate);
  }

  @Override
  public void slowDown() {
    if(currentRate > MIN_RATE){
      currentRate -= DELTA_RATE;
    }
    myTimeline.setRate(currentRate);
  }
}
