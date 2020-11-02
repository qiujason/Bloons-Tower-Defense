package ooga.backend.towers;

import java.util.List;
import ooga.backend.API.Placeable;
import ooga.backend.bloons.Bloons;

public abstract class Tower implements Placeable {
  private int xPosition;
  private int yPosition;
  private int radius;

  public Tower(int myXPosition, int myYPosition, int myRadius){
    setXPosition(myXPosition);
    setYPosition(myYPosition);
    radius = myRadius;
  }

  public void setXPosition(int updateXPos){
    xPosition = updateXPos;
  }
  public void setYPosition(int updateYPos){
    yPosition = updateYPos;
  }
  public int getXPosition(){
    return xPosition;
  }
  public int getYPosition(){
    return yPosition;
  }

  public boolean checkBalloonInRange(List<Bloons> bloonsList){
    for(Bloons bloon : bloonsList){
      double distance = getDistance(bloon.getXPosition(), bloon.getYPosition());
      if(distance <= radius){
        return true;
      }
    }
    return false;
  }

  public abstract void shoot();

  public double getDistance(int bloonX, int bloonY){
    return Math.sqrt(Math.pow(xPosition-bloonX, 2) + Math.pow(yPosition-bloonY, 2));
  }

}
