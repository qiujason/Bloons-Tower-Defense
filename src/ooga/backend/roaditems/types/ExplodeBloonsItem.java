package ooga.backend.roaditems.types;


import ooga.backend.roaditems.RoadItem;
import ooga.backend.roaditems.RoadItemType;
import ooga.visualization.animationhandlers.AnimationHandler;

public class ExplodeBloonsItem extends RoadItem {

  public static int defaultSecondsLeft = 3;

  private int secondsLeft;

  public ExplodeBloonsItem(double myXPosition, double myYPosition) {
    super(myXPosition, myYPosition);
    secondsLeft = (int) (defaultSecondsLeft * AnimationHandler.FRAMES_PER_SECOND);
  }

  @Override
  public boolean shouldRemove() {
    return secondsLeft == 0;
  }

  @Override
  public RoadItemType getType() {
    return RoadItemType.ExplodeBloonsItem;
  }

  // should be called and in the front end
  @Override
  public void update() {
    secondsLeft--;
  }
}
