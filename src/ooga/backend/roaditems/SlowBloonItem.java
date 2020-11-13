package ooga.backend.roaditems;

public class SlowBloonItem extends RoadItem{

  public static int defaultGlueLeft = 20;
  private int bloonsGlueLeft;

  public SlowBloonItem(double myXPosition, double myYPosition) {
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
