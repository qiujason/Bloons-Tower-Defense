package ooga.backend.towers;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.collections.Iterator;
import ooga.backend.darts.Dart;
import ooga.backend.darts.factory.DartFactory;
import ooga.backend.darts.factory.SingleDartFactory;

public abstract class SingleShotTower extends Tower{

  private static final ShootingChoice defaultShootingChoice = ShootingChoice.ClosestBloon;

  private ShootingChoice shootingChoice;

  public SingleShotTower(double myXPosition, double myYPosition, int myRadius) {
    super(myXPosition, myYPosition, myRadius);
    shootingChoice = defaultShootingChoice;
  }

  public ShootingChoice getShootingChoice(){
    return shootingChoice;
  }

  public void updateShootingChoice(ShootingChoice newChoice){
    shootingChoice = newChoice;
  }

  // should only be called IF known that there is a bloon in range OR ELSE will return null
  public Bloon getTarget(BloonsCollection bloonsCollection){
    switch(getShootingChoice()){
      case StrongestBloon: return findStrongestBloon(bloonsCollection);
      case FirstBloon: return findFirstBloon(bloonsCollection);
      case LastBloon: return findLastBloon(bloonsCollection);
      default: return findClosestBloon(bloonsCollection);
    }
  }

  // should only be called IF known that there is a bloon in range OR ELSE will return null
  public Bloon findClosestBloon(BloonsCollection bloonsCollection){
    Iterator iterator = bloonsCollection.createIterator();
    Bloon closestBloon = null;
    double minDistance = Integer.MAX_VALUE;
    while(iterator.hasMore()){
      Bloon bloon = (Bloon) iterator.getNext();
      double distance = getDistance(bloon);
      if(distance > getRadius()){
        continue;
      }
      if(minDistance > distance){
        minDistance = distance;
        closestBloon = bloon;
      }
    }
    return closestBloon;
  }

  // should only be called IF known that there is a bloon in range OR ELSE will return null
  public Bloon findStrongestBloon(BloonsCollection bloonsCollection){
    Iterator iterator = bloonsCollection.createIterator();
    Bloon strongestBloon = null;
    double maxStrength = Integer.MIN_VALUE;
    while(iterator.hasMore()){
      Bloon bloon = (Bloon) iterator.getNext();
      if(getDistance(bloon) > getRadius()){
        continue;
      }
      double strength = bloon.getBloonsType().getRBE();
      if(maxStrength < strength){
        maxStrength = strength;
        strongestBloon = bloon;
      }
    }
    return strongestBloon;
  }

  // should only be called IF known that there is a bloon in range OR ELSE will return null
  public Bloon findFirstBloon(BloonsCollection bloonsCollection){
    Iterator iterator = bloonsCollection.createIterator();
    Bloon firstBloon = null;
    while(iterator.hasMore()){
      Bloon bloon = (Bloon) iterator.getNext();
      if(getDistance(bloon) <= getRadius()){
        firstBloon = bloon;
        break;
      }
    }
    return firstBloon;
  }

  // should only be called IF known that there is a bloon in range OR ELSE will return null
  public Bloon findLastBloon(BloonsCollection bloonsCollection){
    Iterator iterator = bloonsCollection.createIterator();
    Bloon lastBloon = null;
    while(iterator.hasMore()){
      Bloon bloon = (Bloon) iterator.getNext();
      if(getDistance(bloon) <= getRadius()){
        lastBloon = bloon;
      }
    }
    return lastBloon;
  }

  public double findShootXVelocity(Bloon target){
    double distance = getDistance(target);
    return (getXPosition()-target.getXPosition())/distance*getShootingSpeed();
  }

  public double findShootYVelocity(Bloon target){
    double distance = getDistance(target);
    return (getYPosition()-target.getYPosition())/distance*getShootingSpeed();
  }

  @Override
  public List<Dart> shoot(BloonsCollection bloonsCollection) {
    List<Dart> shot = new ArrayList<>();
    if(checkBalloonInRange(bloonsCollection)){
      Bloon target = getTarget(bloonsCollection);
      DartFactory dartFactory = new SingleDartFactory();
      double dartXVelocity = findShootXVelocity(target);
      double dartYVelocity = findShootYVelocity(target);
      shot.add(dartFactory.createDart(getXPosition(), getYPosition(), dartXVelocity, dartYVelocity));
    }
    return shot;
  }

}
