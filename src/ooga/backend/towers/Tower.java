package ooga.backend.towers;

import java.util.List;
import java.util.ResourceBundle;
import ooga.backend.API.TowersAPI;
import ooga.backend.GamePiece;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.projectile.Projectile;
import ooga.visualization.AnimationHandler;
import org.apache.commons.lang3.StringUtils;

public abstract class Tower extends GamePiece implements TowersAPI {

  private double radius;
  private double shootingSpeed;
  private double shootingRestRate;
  private double countRestPeriod;
  private boolean canShoot;

  // if canShoot = true, step function can call shoot method, if not, do not call shoot method

  public Tower(double myXPosition, double myYPosition, double myRadius, double myShootingSpeed,
      double myShootingRestRate){
    super(myXPosition, myYPosition);
    radius = myRadius;
    shootingSpeed = myShootingSpeed;
    shootingRestRate = myShootingRestRate * AnimationHandler.FRAMES_PER_SECOND;
    countRestPeriod = 0;
    canShoot = true;
  }

  public abstract TowerType getTowerType();
  public double getShootingRestRate(){
    return shootingRestRate;
  }
  public double getRadius(){
    return radius;
  }

  // update canShoot to true after resting period has elapsed
  @Override
  public void update() {
    if(!canShoot) {
      countRestPeriod++;
    }
    if(countRestPeriod >= shootingRestRate){
      countRestPeriod = 0;
      canShoot = true;
    }
    else{
      canShoot = false;
    }
  }

  public void updateCanShoot(boolean update){
    canShoot = update;
  }

  public boolean getCanShoot(){
    return canShoot;
  }

  public double getShootingSpeed(){
    return shootingSpeed;
  }

  public boolean checkBalloonInRange(BloonsCollection bloonsCollection){
    GamePieceIterator<Bloon> iterator = bloonsCollection.createIterator();
    while(iterator.hasNext()){
      Bloon bloon = iterator.next();
      double distance = getDistance(bloon);
      if(distance <= radius){
        return true;
      }
    }
    return false;
  }

  public abstract List<Projectile> shoot(BloonsCollection bloonsCollection);

  public double getDistance(GamePiece target){
    return Math.sqrt(Math.pow(getXPosition()-target.getXPosition(), 2) + Math.pow(getYPosition()-target.getYPosition(), 2));
  }

  public void upgradeRadius(){
    String key = "radiusUpgradeMultiplier";
    try{
      ResourceBundle bundle = ResourceBundle.getBundle("towers/" + getTowerType().name());
      upgrade(bundle, key);
    } catch(Exception e){
      radius *= 1.05;
    }
  }

  public void upgradeShootingSpeed(){
    String key = "shootingSpeedUpgradeMultiplier";
    try{
      ResourceBundle bundle = ResourceBundle.getBundle("towers/" + getTowerType().name());
      upgrade(bundle, key);
    } catch(Exception e){
      shootingSpeed *= 1.05;
    }
  }

  public void upgradeShootingRestRate(){
    String key = "shootingRestRateUpgradeMultiplier";
    try{
      ResourceBundle bundle = ResourceBundle.getBundle("towers/" + getTowerType().name());
      upgrade(bundle, key);
    } catch(Exception e){
      shootingRestRate /= 1.05;
    }
  }

  private void upgrade(ResourceBundle bundle, String key){
    if(bundle.containsKey(key) && StringUtils.isNumeric(bundle.getString(key))){
      switch(key){
        case "radiusUpgradeMultiplier": radius *= Integer.valueOf(bundle.getString(key));
        case "shootingSpeedUpgradeMultiplier": shootingSpeed *= Integer.valueOf(bundle.getString(key));
        case "shootingRestRateUpgradeMultiplier": shootingRestRate /= Integer.valueOf(bundle.getString(key));
      }
    } else{
      switch(key){
        case "radiusUpgradeMultiplier": radius *= 1.05;
        case "shootingSpeedUpgradeMultiplier": shootingSpeed *= 1.05;
        case "shootingRestRateUpgradeMultiplier": shootingRestRate /= 1.05;
      }
    }
  }

}
