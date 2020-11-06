package ooga.backend.towers;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.darts.Dart;
import ooga.backend.darts.factory.DartFactory;
import ooga.backend.darts.factory.SingleDartFactory;

public class TackShooter extends SpreadShotTower{

  private static final int numberOfShots = 8;
  private static final int[] dartXVelocity = {};
  private static final int[] dartYVelocity = {};

  public TackShooter(int myXPosition, int myYPosition, int myRadius) {
    super(myXPosition, myYPosition, myRadius);
  }

  @Override
  public List<Dart> shoot(List<Bloon> bloonsList) {
    List<Dart> shot = new ArrayList<>();
    if(checkBalloonInRange(bloonsList)){
      for(int i = 0; i < numberOfShots; i++){
        DartFactory dartFactory = new SingleDartFactory();
        //shot.add(dartFactory.createDart(getXPosition(), getYPosition(), dartXVelocity[i], dartYVelocity[i]));
      }
      //DartFactory dartFactory = new SingleDartFactory();
      //double dartXVelocity = findShootXVelocity(target);
      //double dartYVelocity = findShootYVelocity(target);
      //shot.add(dartFactory.createDart(getXPosition(), getYPosition(), dartXVelocity, dartYVelocity));
    }
    return shot;
  }
}
