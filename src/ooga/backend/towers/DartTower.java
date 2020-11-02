package ooga.backend.towers;

import java.util.List;
import ooga.backend.bloons.Bloons;
import ooga.backend.factory.DartFactory;
import ooga.backend.factory.SingleDartFactory;

public class DartTower extends SingleShotTower {

  public DartTower(int myXPosition, int myYPosition, int myRadius) {
    super(myXPosition, myYPosition, myRadius);
  }

  @Override
  public void shoot(List<Bloons> bloonsList) {
    if(checkBalloonInRange(bloonsList)){
      Bloons target = findClosestBloon(bloonsList);
      DartFactory dartFactory = new SingleDartFactory();
      double dartXVelocity = findShootXVelocity(target);
      double dartYVelocity = findShootYVelocity(target);
      dartFactory.createDart(getXPosition(), getYPosition(), dartXVelocity, dartYVelocity);
    }
  }
}
