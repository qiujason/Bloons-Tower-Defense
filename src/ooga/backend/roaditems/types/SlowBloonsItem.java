package ooga.backend.roaditems.types;

import ooga.backend.roaditems.RoadItem;
import ooga.backend.roaditems.RoadItemType;

public class SlowBloonsItem extends RoadItem {

  public static int defaultGlueLeft = 10;
  private int bloonsGlueLeft;

  public SlowBloonsItem(double myXPosition, double myYPosition) {
    super(myXPosition, myYPosition);
    bloonsGlueLeft = defaultGlueLeft;
  }

  @Override
  public boolean shouldRemove() {
    return bloonsGlueLeft == 0;
  }

  @Override
  public RoadItemType getType() {
    return RoadItemType.SlowBloonsItem;
  }


  // should be called and in the front end when a bloon touches a spike
  // decrease velocity for bloon
  @Override
  public void update() {
    bloonsGlueLeft--;
  }
}
