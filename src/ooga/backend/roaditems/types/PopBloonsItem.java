/**
 * Class for PopBloonsItem that should pop bloons that pass by the road item
 * @author Annshine
 */
package ooga.backend.roaditems.types;

import ooga.backend.roaditems.RoadItem;
import ooga.backend.roaditems.RoadItemType;

public class PopBloonsItem extends RoadItem {

  public static final int defaultSpikes = 10;
  private int spikesLeft;

  /**
   * Constructor for PopBloonsItem
   * @param myXPosition
   * @param myYPosition
   */
  public PopBloonsItem(double myXPosition, double myYPosition) {
    super(myXPosition, myYPosition);
    spikesLeft = defaultSpikes;
  }

  /**
   *
   * @return if spikesLeft == 0
   */
  @Override
  public boolean shouldRemove() {
    return spikesLeft == 0;
  }

  /**
   *
   * @return type of road item
   */
  @Override
  public RoadItemType getType() {
    return RoadItemType.PopBloonsItem;
  }
  /**
   * should be called and in the front end when a bloon touches a spike
   */
  @Override
  public void update() {
    spikesLeft--;
  }

}
