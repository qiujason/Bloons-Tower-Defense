package ooga.backend.roaditems;

import ooga.backend.API.RoadItemsAPI;
import ooga.backend.GamePiece;

public abstract class RoadItem extends GamePiece implements RoadItemsAPI {

  public RoadItem(double myXPosition, double myYPosition) {
    super(myXPosition, myYPosition);
  }

  public abstract boolean shouldRemove();

  public abstract RoadItemType getType();
}
