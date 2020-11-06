package ooga.backend.towers;

import java.util.List;
import ooga.backend.bloons.Bloons;

public abstract class SingleShotTower extends Tower{


  public SingleShotTower(double myXPosition, double myYPosition, int myRadius) {
    super(myXPosition, myYPosition, myRadius);
  }

  // should only be called IF known that there is a bloon in range
  public Bloons findClosestBloon(List<Bloons> bloonsList){
    Bloons closestBloon = null;
    double minDistance = Integer.MAX_VALUE;
    for(Bloons bloon : bloonsList){
      double distance = getDistance(bloon);
      if(minDistance > distance){
        minDistance = distance;
        closestBloon = bloon;
      }
    }
    return closestBloon;
  }

  public double findShootXVelocity(Bloons target){
    double distance = getDistance(target);
    return (getXPosition()-target.getXPosition())/distance*getShootingSpeed();
  }

  public double findShootYVelocity(Bloons target){
    double distance = getDistance(target);
    return (getYPosition()-target.getYPosition())/distance*getShootingSpeed();
  }

}
