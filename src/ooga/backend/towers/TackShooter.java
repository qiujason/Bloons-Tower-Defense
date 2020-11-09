package ooga.backend.towers;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.darts.Dart;
import ooga.backend.darts.factory.DartFactory;
import ooga.backend.darts.factory.SingleDartFactory;

public class TackShooter extends SpreadShotTower{

  private static final int numberOfShots = 8;
  private static final int degreeIncrementPerShot = 45;

  public TackShooter(int myXPosition, int myYPosition, int myRadius) {
    super(myXPosition, myYPosition, myRadius);
  }

  @Override
  public List<Dart> shoot(BloonsCollection bloonsCollection) {
    List<Dart> shot = new ArrayList<>();
    if(checkBalloonInRange(bloonsCollection)){
      for(int i = 0; i < numberOfShots; i++){
        DartFactory dartFactory = new SingleDartFactory();
        double dartXVelocity = Math.cos(i*degreeIncrementPerShot);
        double dartYVelocity = Math.sin(i*degreeIncrementPerShot);;
        shot.add(dartFactory.createDart(getXPosition(), getYPosition(), dartXVelocity, dartYVelocity));
      }
    }
    return shot;
  }
}
