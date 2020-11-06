package ooga.backend.towers;

import java.util.List;
import ooga.backend.API.GamePiece;
import ooga.backend.bloons.Bloons;
import ooga.backend.darts.Dart;

public abstract class Tower implements GamePiece {

  private static final double defaultShootingSpeed = 20.0;

  private double xPosition;
  private double yPosition;
  private int radius;
  private double shootingSpeed;

  public Tower(double myXPosition, double myYPosition, int myRadius){
    setXPosition(myXPosition);
    setYPosition(myYPosition);
    radius = myRadius;
    shootingSpeed = defaultShootingSpeed;
  }

  @Override
  public void setXPosition(double updateXPos){
    xPosition = updateXPos;
  }

  @Override
  public void setYPosition(double updateYPos){
    yPosition = updateYPos;
  }

  @Override
  public double getXPosition(){
    return xPosition;
  }

  @Override
  public double getYPosition(){
    return yPosition;
  }

  @Override
  public void update() {

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
