/**
 * Class represents SlowBloonsItem to slow down bloons that touch the item
 */
package ooga.backend.roaditems.types;

import ooga.backend.roaditems.RoadItem;
import ooga.backend.roaditems.RoadItemType;

public class SlowBloonsItem extends RoadItem {

  public static final int defaultGlueLeft = 10;
  private int bloonsGlueLeft;

  /**
   * Constructor for SlowBloonsItem
   * @param myXPosition
   * @param myYPosition
   */
  public SlowBloonsItem(double myXPosition, double myYPosition) {
    super(myXPosition, myYPosition);
    bloonsGlueLeft = defaultGlueLeft;
  }

  /**
   *
   * @return bloonsGlueLeft == 0
   */
  @Override
  public boolean shouldRemove() {
    return bloonsGlueLeft == 0;
  }

  /**
   *
   * @return RoadItemType.SlowBloonsItem
   */
  @Override
  public RoadItemType getType() {
    return RoadItemType.SlowBloonsItem;
  }

  /**
   * should be called and in the front end when a bloon touches a spike
   */
  @Override
  public void update() {
    bloonsGlueLeft--;
  }
}
