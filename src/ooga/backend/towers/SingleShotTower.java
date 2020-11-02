package ooga.backend.towers;

import java.util.List;
import ooga.backend.bloons.Bloons;

public abstract class SingleShotTower extends Tower{


  public SingleShotTower(int myXPosition, int myYPosition, int myRadius) {
    super(myXPosition, myYPosition, myRadius);
  }

  // should only be called IF known that there is a bloon in range
  public Bloons findClosestBloon(List<Bloons> bloonsList){
    Bloons closestBloon = null;
    double minDistance = Integer.MAX_VALUE;
    for(Bloons bloon : bloonsList){
      double distance = getDistance(bloon.getXPosition(), bloon.getYPosition());
      if(minDistance > distance){
        minDistance = distance;
        closestBloon = bloon;
      }
    }
    return closestBloon;
  }

}
