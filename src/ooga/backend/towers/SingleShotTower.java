package ooga.backend.towers;

import java.util.List;
import ooga.backend.bloons.Bloon;

public abstract class SingleShotTower extends Tower{


  public SingleShotTower(double myXPosition, double myYPosition, int myRadius) {
    super(myXPosition, myYPosition, myRadius);
  }

  // should only be called IF known that there is a bloon in range
  public Bloon findClosestBloon(List<Bloon> bloonsList){
    Bloon closestBloon = null;
    double minDistance = Integer.MAX_VALUE;
    for(Bloon bloon : bloonsList){
      double distance = getDistance(bloon);
      if(minDistance > distance){
        minDistance = distance;
        closestBloon = bloon;
      }
    }
    return closestBloon;
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
