package ooga.backend.towers;

import java.util.List;
import ooga.backend.API.GamePiece;
import ooga.backend.bloons.Bloons;
import ooga.backend.darts.Dart;

public abstract class Tower implements GamePiece {

  private static final double defaultShootingSpeed = 20.0;

  private int xPosition;
  private int yPosition;
  private int radius;
  private double shootingSpeed;

  public Tower(int myXPosition, int myYPosition, int myRadius){
    setXPosition(myXPosition);
    setYPosition(myYPosition);
    radius = myRadius;
    shootingSpeed = defaultShootingSpeed;
  }

  @Override
  public void setXPosition(int updateXPos){
    xPosition = updateXPos;
  }

  @Override
  public void setYPosition(int updateYPos){
    yPosition = updateYPos;
  }

  @Override
  public int getXPosition(){
    return xPosition;
  }

  @Override
  public int getYPosition(){
    return yPosition;
  }

  public double getShootingSpeed(){
    return shootingSpeed;
  }

  public boolean checkBalloonInRange(List<Bloons> bloonsList){
    for(Bloons bloon : bloonsList){
      double distance = getDistance(bloon);
      if(distance <= radius){
        return true;
      }
    }
    return false;
  }

  public abstract List<Dart> shoot(List<Bloons> bloonsList);

  public double getDistance(Bloons target){
    return Math.sqrt(Math.pow(xPosition-target.getXPosition(), 2) + Math.pow(yPosition-target.getYPosition(), 2));
  }

}
