/**
 * Class represents the abstract class for all Towers
 * Should be extended to create specific types of towers
 * @author Annshine
 */
package ooga.backend.towers;

import java.util.ResourceBundle;
import ooga.backend.API.TowersAPI;
import ooga.backend.ConfigurationException;
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

  /**
   * Constructor for Tower specifying xPostition, yPosition, radius, shooting speed, shooting rest rate
   * @param myXPosition
   * @param myYPosition
   * @param myRadius
   * @param myShootingSpeed
   * @param myShootingRestRate
   */
  public Tower(double myXPosition, double myYPosition, double myRadius, double myShootingSpeed,
      double myShootingRestRate) {
    super(myXPosition, myYPosition);
    radius = myRadius;
    shootingSpeed = myShootingSpeed;
    shootingRestRate = myShootingRestRate * AnimationHandler.FRAMES_PER_SECOND;
    countRestPeriod = 0;
    ifRestPeriod = false;
    totalUpgradeCost = 0;
    numberOfUpgrades = 0;
  }

  /**
   * Purpose: Method should be used to return the tower type
   * @return a TowerType that indicates what kind of Tower the class is
   */
  public abstract TowerType getTowerType();

  /**
   * Purpose: Method should be used to get the shooting rest rate of tower
   * @return double - shootingRestRate of tower
   */
  public double getShootingRestRate() {
    return shootingRestRate;
  }

  /**
   * Purpose: Method should be used to get the radius of tower
   * @return double - radius of tower
   */
  public double getRadius() {
    return radius;
  }


  /**
   * Purpose: Method should be called to update the rest period
   * It will update canShoot to true after resting period has elapsed to indicate the tower can shoot
   */
  @Override
  public void update() {
    if (ifRestPeriod) {
      countRestPeriod++;
      if (countRestPeriod >= shootingRestRate) {
        countRestPeriod = 0;
        ifRestPeriod = false;
      }
    }
  }

  /**
   * Purpose: Method should be used to update ifRestPeriod
   * @param update - new boolean to update ifRestPeriod
   */
  public void updateIfRestPeriod(boolean update) {
    ifRestPeriod = update;
  }

  /**
   * Purpose: Method should be used to get ifRestPeriod
   * @return boolean - ifRestPeriod
   */
  public boolean isIfRestPeriod() {
    return ifRestPeriod;
  }

  /**
   * Purpose: Method should be used to get the shooting speed of tower
   * @return double - shooting speed of tower
   */
  public double getShootingSpeed() {
    return shootingSpeed;
  }

  /**
   * Purpose: Method should be used to check whether there is a bloon in range within bloonsCollection
   * @param bloonsCollection
   * @return a boolean of whether there is a bloon in range
   */
  public boolean checkBalloonInRange(BloonsCollection bloonsCollection) {
    GamePieceIterator<Bloon> iterator = bloonsCollection.createIterator();
    while (iterator.hasNext()) {
      Bloon bloon = iterator.next();
      if (ifCamoBloon(bloon) || bloon.isDead()) {
        continue;
      }
      double distance = getDistance(bloon);
      if (distance <= radius) {
        return true;
      }
    }
    return false;
  }

  /**
   * Purpose: Method should be used to create projectiles and add to projectilesCollection
   * @param bloonsCollection: the collection to check if there is bloon in range
   * @param projectilesCollection: the collection to add new created projectiles
   * @return the Bloon to target at
   * @throws ConfigurationException
   */
  public abstract Bloon shoot(BloonsCollection bloonsCollection,
      ProjectilesCollection projectilesCollection)
      throws ConfigurationException;

  /**
   * Purpose: Method should be used to distance between tower and target
   * @param target: GamePiece to calculate distance difference
   * @return double - to represent distance between tower and target
   */
  public double getDistance(GamePiece target) {
    return Math.sqrt(Math.pow(getXPosition() - target.getXPosition(), 2) + Math
        .pow(getYPosition() - target.getYPosition(), 2));
  }

  /**
   * Purpose: Method should be used to get cost of upgrade depending on upgrade choice
   * @param choice: Upgrade choice to check the costs for
   * @return the cost of an upgrade
   */
  public int getCostOfUpgrade(UpgradeChoice choice) {
    int returnedCost = defaultUpgradeCost;
    try {
      ResourceBundle bundle = ResourceBundle.getBundle("towers/" + getTowerType().name());
      String key = choice.name() + "Cost";
      if (bundle.containsKey(key) && StringUtils.isNumeric(bundle.getString(key))) {
        returnedCost = Integer.parseInt(bundle.getString(key));
      }
    } catch (Exception e) {
    }
    return returnedCost;
  }

  /**
   * Purpose: Method should be used to check if an upgrade can be upgraded
   * @return boolean representing whether an upgrade is possible
   */
  public boolean canPerformUpgrade() {
    return numberOfUpgrades < numberOfAllowedUpgrades;
  }

  /**
   * Purpose: Method should be used to perform an upgrade with a chosen upgrade choice
   * @param choice - the choice of upgrade type
   */
  public void performUpgrade(UpgradeChoice choice) {
    if (!canPerformUpgrade()) {
      return;
    }
    numberOfUpgrades++;
    try {
      ResourceBundle bundle = ResourceBundle.getBundle("towers/" + getTowerType().name());
      upgradeWithSelectedChoice(bundle, choice.name() + "Multiplier");
      upgradeWithSelectedChoice(bundle, choice.name() + "Cost");
    } catch (Exception e) {
      if (choice == UpgradeChoice.RadiusUpgrade) {
        radius *= defaultUpgradeMultiplier;
      } else if (choice == UpgradeChoice.ShootingSpeedUpgrade) {
        shootingSpeed *= defaultUpgradeMultiplier;
      } else if (choice == UpgradeChoice.ShootingRestRateUpgrade) {
        shootingRestRate /= defaultUpgradeMultiplier;
      }
      totalUpgradeCost += defaultUpgradeCost;
    }
  }

  private void upgradeWithSelectedChoice(ResourceBundle bundle, String key) {
    if (bundle.containsKey(key) && NumberUtils.isCreatable(bundle.getString(key))) {
      double value = Double.parseDouble(bundle.getString(key));
      upgradeHelper(key, value, value);
    } else {
      upgradeHelper(key, defaultUpgradeMultiplier, defaultUpgradeCost);
    }
  }

  private void upgradeHelper(String key, double currUpgradeMultiplier, double currUpgradeCost) {
    switch (key) {
      case "RadiusUpgradeMultiplier":
        this.radius *= currUpgradeMultiplier;
        break;
      case "ShootingSpeedUpgradeMultiplier":
        this.shootingSpeed *= currUpgradeMultiplier;
        break;
      case "ShootingRestRateUpgradeMultiplier":
        this.shootingRestRate /= currUpgradeMultiplier;
        break;
      case "RadiusUpgradeCost":
      case "ShootingSpeedUpgradeCost":
      case "ShootingRestRateUpgradeCost":
        this.totalUpgradeCost += currUpgradeCost;
        break;
    }
  }

  /**
   * Purpose: Method should be used to check if a bloon is camo and the tower is not camo projectile shooter
   * @param bloon - bloon to check if it is camo
   * @return boolean represent if tower is not CamoProjectileShooter & the bloon is Camo
   */
  public boolean ifCamoBloon(Bloon bloon) {
    return getTowerType() != TowerType.CamoProjectileShooter && bloon.getBloonsType().specials() ==
        Specials.Camo;
  }

  /**
   * Purpose: Method should be used to set the projectile type for the tower
   * @param update: the projectile type to update to
   */
  public void setProjectileType(ProjectileType update) {
    projectileType = update;
  }

  /**
   * Purpose: Method should be used to return the projectile type shot from the tower
   * @return ProjectileType that the tower shoots
   */
  public ProjectileType getProjectileType() {
    return projectileType;
  }

  /**
   * Purpose: Method should return the total upgrade costs for upgrades that have been used
   * @return integer - total upgrade costs
   */
  public int getTotalUpgradeCost() {
    return totalUpgradeCost;
  }
}
