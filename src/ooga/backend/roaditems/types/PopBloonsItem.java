package ooga.backend.roaditems.types;

import ooga.backend.roaditems.RoadItem;
import ooga.backend.roaditems.RoadItemType;

public class PopBloonsItem extends RoadItem {

  public static final int defaultSpikes = 10;
  private int spikesLeft;

  public PopBloonsItem(double myXPosition, double myYPosition) {
    super(myXPosition, myYPosition);
    spikesLeft = defaultSpikes;
  }

  @Override
  public boolean shouldRemove() {
    return spikesLeft == 0;
  }

  @Override
  public RoadItemType getType() {
    return RoadItemType.PopBloonsItem;
  }

  // should be called and in the front end when a bloon touches a spike
  // remove a bloon + pop a bloon
  @Override
  public void update() {
    spikesLeft--;
  }

}
