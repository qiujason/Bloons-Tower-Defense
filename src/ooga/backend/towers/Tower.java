package ooga.backend.towers;

import java.util.ResourceBundle;
import ooga.backend.API.TowersAPI;
import ooga.backend.GamePiece;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.types.Specials;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.visualization.animationhandlers.AnimationHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public abstract class Tower extends GamePiece implements TowersAPI {

  private static final double defaultUpgradeMultiplier = 1.05;
  private static final int defaultUpgradeCost = 150;
  private static final int numberOfAllowedUpgrades = 6;

  private double radius;
  private double shootingSpeed;
  private double shootingRestRate;
  private double countRestPeriod;
  private boolean ifRestPeriod;
  private ProjectileType projectileType;
  private int totalUpgradeCost;
  private int numberOfUpgrades;

  // if canShoot = true, step function can call shoot method, if not, do not call shoot method

  public Tower(double myXPosition, double myYPosition, double myRadius, double myShootingSpeed,
      double myShootingRestRate){
    super(myXPosition, myYPosition);
    radius = myRadius;
    shootingSpeed = myShootingSpeed;
    shootingRestRate = myShootingRestRate * AnimationHandler.FRAMES_PER_SECOND;
    countRestPeriod = 0;
    ifRestPeriod = false;
    totalUpgradeCost = 0;
    numberOfUpgrades = 0;
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
    if(ifRestPeriod) {
      countRestPeriod++;
      if(countRestPeriod >= shootingRestRate){
        countRestPeriod = 0;
        ifRestPeriod = false;
      }
    }
  }

  public void updateIfRestPeriod(boolean update){
    ifRestPeriod = update;
  }

  public boolean isIfRestPeriod(){
    return ifRestPeriod;
  }

  public double getShootingSpeed(){
    return shootingSpeed;
  }

  public boolean checkBalloonInRange(BloonsCollection bloonsCollection){
    GamePieceIterator<Bloon> iterator = bloonsCollection.createIterator();
    while(iterator.hasNext()){
      Bloon bloon = iterator.next();
      if(ifCamoBloon(bloon) || bloon.isDead()){
        continue;
      }
      double distance = getDistance(bloon);
      if(distance <= radius){
        return true;
      }
    }
    return false;
  }

  public abstract Bloon shoot(BloonsCollection bloonsCollection, ProjectilesCollection projectilesCollection);

  public double getDistance(GamePiece target){
    return Math.sqrt(Math.pow(getXPosition()-target.getXPosition(), 2) + Math.pow(getYPosition()-target.getYPosition(), 2));
  }

  public int getCostOfUpgrade(UpgradeChoice choice){
    int returnedCost = defaultUpgradeCost;
    try {
      ResourceBundle bundle = ResourceBundle.getBundle("towers/" + getTowerType().name());
      String key = choice.name() + "Cost";
      if(bundle.containsKey(key) && StringUtils.isNumeric(bundle.getString(key))){
        returnedCost = Integer.parseInt(bundle.getString(key));
      }
    } catch (Exception e) {
    }
    return returnedCost;
  }

  public boolean canPerformUpgrade(){
    return numberOfUpgrades < numberOfAllowedUpgrades;
  }

  public void performUpgrade(UpgradeChoice choice){
    if(!canPerformUpgrade()){
      return;
    }
    numberOfUpgrades++;
    try{
      ResourceBundle bundle = ResourceBundle.getBundle("towers/" + getTowerType().name());
      upgradeWithSelectedChoice(bundle, choice.name() + "Multiplier");
      upgradeWithSelectedChoice(bundle, choice.name() + "Cost");
    } catch(Exception e){
      if(choice == UpgradeChoice.RadiusUpgrade){
        radius *= defaultUpgradeMultiplier;
      } else if(choice == UpgradeChoice.ShootingSpeedUpgrade){
        shootingSpeed *= defaultUpgradeMultiplier;
      } else if(choice == UpgradeChoice.ShootingRestRateUpgrade){
        shootingRestRate /= defaultUpgradeMultiplier;
      }
      totalUpgradeCost += defaultUpgradeCost;
    }
  }

  private void upgradeWithSelectedChoice(ResourceBundle bundle, String key){
    if(bundle.containsKey(key) && NumberUtils.isCreatable(bundle.getString(key))){
      double value = Double.parseDouble(bundle.getString(key));
      upgradeHelper(key, value, value);
    } else{
      upgradeHelper(key, defaultUpgradeMultiplier, defaultUpgradeCost);
    }
  }

  private void upgradeHelper(String key, double currUpgradeMultiplier, double currUpgradeCost) {
    switch(key){
      case "RadiusUpgradeMultiplier":
        this.radius *= currUpgradeMultiplier; break;
      case "ShootingSpeedUpgradeMultiplier":
        this.shootingSpeed *= currUpgradeMultiplier; break;
      case "ShootingRestRateUpgradeMultiplier":
        this.shootingRestRate /= currUpgradeMultiplier; break;
      case "RadiusUpgradeCost":
      case "ShootingSpeedUpgradeCost":
      case "ShootingRestRateUpgradeCost":
        this.totalUpgradeCost += currUpgradeCost; break;
    }
  }

  public boolean ifCamoBloon(Bloon bloon){
    return getTowerType() != TowerType.CamoProjectileShooter && bloon.getBloonsType().specials().contains(
        Specials.Camo);
  }

  public void setProjectileType(ProjectileType update){
    projectileType = update;
  }

  public ProjectileType getProjectileType(){
    return projectileType;
  }

  public int getTotalUpgradeCost(){
    return totalUpgradeCost;
  }
}
