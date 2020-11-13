package ooga.backend.roaditems;

import ooga.backend.GamePiece;

public abstract class RoadItem extends GamePiece {
  public RoadItem(double myXPosition, double myYPosition){
    super(myXPosition, myYPosition);
  }
  public abstract boolean shouldRemove();
  public abstract RoadItemType getType();
}
