package ooga.backend.towers;

import java.util.List;
import ooga.backend.API.GamePiece;
import ooga.backend.API.TowersAPI;
import ooga.backend.bloons.collection.BloonsCollection;
import ooga.backend.collections.Iterator;
import ooga.backend.projectile.Projectile;

public abstract class Tower implements GamePiece, TowersAPI {

  private double xPosition;
  private double yPosition;
  private double radius;
  private double shootingSpeed;
  private double shootingRestRate;
  private double countRestPeriod;
  private boolean canShoot;

  // if canShoot = true, step function can call shoot method, if not, do not call shoot method

  public Tower(double myXPosition, double myYPosition, double myRadius, double myShootingSpeed,
      double myShootingRestRate){
    setXPosition(myXPosition);
    setYPosition(myYPosition);
    radius = myRadius;
    shootingSpeed = myShootingSpeed;
    shootingRestRate = myShootingRestRate;
    countRestPeriod = 0;
    canShoot = true;
  }

  public abstract TowerType getTowerType();

  public double getRadius(){
    return radius;
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

  // update canShoot to true after resting period has elapsed
  @Override
  public void update() {
    if(!canShoot) {
      countRestPeriod++;
    }
    if(countRestPeriod == shootingRestRate){
      countRestPeriod = 0;
      canShoot = true;
    }
  }

  public boolean getCanShoot(){
    return canShoot;
  }

  public double getShootingSpeed(){
    return shootingSpeed;
  }

  public boolean checkBalloonInRange(BloonsCollection bloonsCollection){
    Iterator iterator = bloonsCollection.createIterator();
    while(iterator.hasMore()){
      GamePiece bloon = iterator.getNext();
      double distance = getDistance(bloon);
      if(distance <= radius){
        return true;
      }
    }
    return false;
  }

  public abstract List<Projectile> shoot(BloonsCollection bloonsCollection);

  public double getDistance(GamePiece target){
    return Math.sqrt(Math.pow(xPosition-target.getXPosition(), 2) + Math.pow(yPosition-target.getYPosition(), 2));
  }

}
