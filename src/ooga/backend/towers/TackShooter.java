package ooga.backend.towers;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.bloons.Bloons;
import ooga.backend.darts.Dart;
import ooga.backend.factory.DartFactory;
import ooga.backend.factory.SingleDartFactory;

public class TackShooter extends SpreadShotTower{

  private static final int numberOfShots = 8;
  private static final int[] dartXVelocity = {};
  private static final int[] dartYVelocity = {};

  public TackShooter(int myXPosition, int myYPosition, int myRadius) {
    super(myXPosition, myYPosition, myRadius);
  }

  @Override
  public List<Dart> shoot(List<Bloons> bloonsList) {
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
