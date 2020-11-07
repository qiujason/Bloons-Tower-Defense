package ooga.backend.towers;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.darts.Dart;
import ooga.backend.darts.factory.DartFactory;
import ooga.backend.darts.factory.SingleDartFactory;

public class DartTower extends SingleShotTower {

  public DartTower(double myXPosition, double myYPosition, int myRadius) {
    super(myXPosition, myYPosition, myRadius);
  }


  @Override
  public List<Dart> shoot(List<Bloon> bloonsList) {
    List<Dart> shot = new ArrayList<>();
    if(checkBalloonInRange(bloonsList)){
      Bloon target = null;
      if(getShootingChoice() == ShootingChoice.ClosestBloon){
        target = findClosestBloon(bloonsList);
      } else if(getShootingChoice() == ShootingChoice.StrongestBloon){
        target = findStrongestBloon(bloonsList);
      } else if(getShootingChoice() == ShootingChoice.FirstBloon){
        target = findFirstBloon(bloonsList);
      } else if(getShootingChoice() == ShootingChoice.LastBloon) {
        target = findLastBloon(bloonsList);
      }
      DartFactory dartFactory = new SingleDartFactory();
      double dartXVelocity = findShootXVelocity(target);
      double dartYVelocity = findShootYVelocity(target);
      shot.add(dartFactory.createDart(getXPosition(), getYPosition(), dartXVelocity, dartYVelocity));
    }
    return shot;
  }
}
