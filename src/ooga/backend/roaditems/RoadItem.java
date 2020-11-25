/**
 * Abstract class for RoadItems that should be extended to create different subclasses
 * @author Annshine
 */
package ooga.backend.roaditems;

import ooga.backend.API.RoadItemsAPI;
import ooga.backend.GamePiece;

public abstract class RoadItem extends GamePiece implements RoadItemsAPI {

  /**
   * Constructor of road item
   * @param myXPosition
   * @param myYPosition
   */
  public RoadItem(double myXPosition, double myYPosition) {
    super(myXPosition, myYPosition);
  }

  /**
   *
   * @return whether a road item should be removed
   */
  public abstract boolean shouldRemove();

  /**
   *
   * @return type of road item
   */
  public abstract RoadItemType getType();
}
