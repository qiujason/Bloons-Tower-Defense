package ooga.backend.towers;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.bloons.Bloons;
import ooga.backend.darts.Dart;
import ooga.backend.darts.factory.DartFactory;
import ooga.backend.darts.factory.SingleDartFactory;

public class DartTower extends SingleShotTower {

  public DartTower(double myXPosition, double myYPosition, int myRadius) {
    super(myXPosition, myYPosition, myRadius);
  }


  @Override
  public List<Dart> shoot(List<Bloons> bloonsList) {
    List<Dart> shot = new ArrayList<>();
    if(checkBalloonInRange(bloonsList)){
      Bloons target = findClosestBloon(bloonsList);
      DartFactory dartFactory = new SingleDartFactory();
      double dartXVelocity = findShootXVelocity(target);
      double dartYVelocity = findShootYVelocity(target);
      shot.add(dartFactory.createDart(getXPosition(), getYPosition(), dartXVelocity, dartYVelocity));
    }
    return shot;
  }
}
