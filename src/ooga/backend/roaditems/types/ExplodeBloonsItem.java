/**
 * Class for ExplodeBloonsItem that should pop surrounding bloons
 */
package ooga.backend.roaditems.types;


import ooga.backend.roaditems.RoadItem;
import ooga.backend.roaditems.RoadItemType;
import ooga.visualization.animationhandlers.AnimationHandler;

public class ExplodeBloonsItem extends RoadItem {

  public static final int defaultSecondsLeft = 3;

  private int secondsLeft;

  /**
   * Constructor for ExplodeBloonsItem
   * @param myXPosition
   * @param myYPosition
   */
  public ExplodeBloonsItem(double myXPosition, double myYPosition) {
    super(myXPosition, myYPosition);
    secondsLeft = (int) (defaultSecondsLeft * AnimationHandler.FRAMES_PER_SECOND);
  }

  /**
   *
   * @return if secondsLeft == 0
   */
  @Override
  public boolean shouldRemove() {
    return secondsLeft == 0;
  }

  /**
   *
   * @return type of road item
   */
  @Override
  public RoadItemType getType() {
    return RoadItemType.ExplodeBloonsItem;
  }


  /**
   * Method should be called in front end to update the seconds of the road item
   */
  @Override
  public void update() {
    secondsLeft--;
  }
}
