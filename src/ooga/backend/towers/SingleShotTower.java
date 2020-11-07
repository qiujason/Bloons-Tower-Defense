package ooga.backend.towers;

import java.util.List;
import ooga.backend.bloons.Bloon;

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
  public Bloon getTarget(List<Bloon> bloonsList){
    switch(getShootingChoice()){
      case StrongestBloon: return findStrongestBloon(bloonsList);
      case FirstBloon: return findFirstBloon(bloonsList);
      case LastBloon: return findLastBloon(bloonsList);
      default: return findClosestBloon(bloonsList);
    }
  }

  // should only be called IF known that there is a bloon in range OR ELSE will return null
  public Bloon findClosestBloon(List<Bloon> bloonsList){
    Bloon closestBloon = null;
    double minDistance = Integer.MAX_VALUE;
    for(Bloon bloon : bloonsList){
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
  public Bloon findStrongestBloon(List<Bloon> bloonsList){
    Bloon strongestBloon = null;
    double maxStrength = Integer.MIN_VALUE;
    for(Bloon bloon : bloonsList){
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
  public Bloon findFirstBloon(List<Bloon> bloonsList){
    Bloon firstBloon = null;
    for(Bloon bloon : bloonsList){
      if(getDistance(bloon) <= getRadius()){
        firstBloon = bloon;
        break;
      }
    }
    return firstBloon;
  }

  // should only be called IF known that there is a bloon in range OR ELSE will return null
  public Bloon findLastBloon(List<Bloon> bloonsList){
    Bloon lastBloon = null;
    for(Bloon bloon : bloonsList){
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

}
