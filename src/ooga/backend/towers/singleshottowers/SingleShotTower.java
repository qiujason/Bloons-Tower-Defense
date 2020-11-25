/**
 * This class is an abstract class that represents a single shot shooter that fires one projectile at a time
 * @author Annshine
 */
package ooga.backend.towers.singleshottowers;

import ooga.backend.ConfigurationException;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.projectile.factory.ProjectileFactory;
import ooga.backend.projectile.factory.SingleProjectileFactory;
import ooga.backend.towers.ShootingChoice;
import ooga.backend.towers.Tower;

public abstract class SingleShotTower extends Tower {

  private static final ShootingChoice defaultShootingChoice = ShootingChoice.FirstBloon;
  private static final int velocityAdjustment = 10;
  private static final int hundred_eight_degrees = 180;

  private ShootingChoice shootingChoice;

  /**
   * Constructor for a single shot tower
   * @param myXPosition
   * @param myYPosition
   * @param myRadius
   * @param myShootingSpeed
   * @param myShootingRestRate
   */
  public SingleShotTower(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
    shootingChoice = defaultShootingChoice;
    setProjectileType(ProjectileType.SingleTargetProjectile);
  }

  /**
   * Method should be used to get the shooting choice
   * @return shootingChoice of the tower
   */
  public ShootingChoice getShootingChoice() {
    return shootingChoice;
  }

  /**
   * Method should be used to update shooting choice
   * @param newChoice - the new shooting choice update
   */
  public void updateShootingChoice(ShootingChoice newChoice) {
    shootingChoice = newChoice;
  }

  // should only be called IF known that there is a bloon in range OR ELSE will return null

  /**
   * Method should be used to get the target of a tower
   * @param bloonsCollection
   * @return Bloon - the target of the tower
   */
  public Bloon getTarget(BloonsCollection bloonsCollection) {
    return switch (getShootingChoice()) {
      case StrongestBloon -> findStrongestBloon(bloonsCollection);
      case ClosestBloon -> findClosestBloon(bloonsCollection);
      case LastBloon -> findLastBloon(bloonsCollection);
      default -> findFirstBloon(bloonsCollection);
    };
  }


  /**
   * Assumption: should only be called IF known that there is a bloon in range OR ELSE will return null
   * Method should be used to find the closest bloon in bloons collection within the range of the tower
   * @param bloonsCollection the bloon collection to search for closest bloon
   * @return the closest bloon
   */
  public Bloon findClosestBloon(BloonsCollection bloonsCollection) {
    GamePieceIterator<Bloon> iterator = bloonsCollection.createIterator();
    Bloon closestBloon = null;
    double minDistance = Integer.MAX_VALUE;
    while (iterator.hasNext()) {
      Bloon bloon = iterator.next();
      double distance = getDistance(bloon);
      if (ifCamoBloon(bloon) || distance > getRadius()) {
        continue;
      }
      if (minDistance > distance) {
        minDistance = distance;
        closestBloon = bloon;
      }
    }

    return closestBloon;
  }

  /**
   * Assumption: should only be called IF known that there is a bloon in range OR ELSE will return null
   * Method should be used to find the strongest bloon in bloons collection within the range of the tower
   * @param bloonsCollection the bloon collection to search for strongest bloon
   * @return the strongest bloon
   */
  public Bloon findStrongestBloon(BloonsCollection bloonsCollection) {
    GamePieceIterator<Bloon> iterator = bloonsCollection.createIterator();
    Bloon strongestBloon = null;
    double maxStrength = Integer.MIN_VALUE;
    while (iterator.hasNext()) {
      Bloon bloon = iterator.next();
      if (ifCamoBloon(bloon) || getDistance(bloon) > getRadius()) {
        continue;
      }
      double strength = bloon.getBloonsType().RBE();
      if (maxStrength < strength) {
        maxStrength = strength;
        strongestBloon = bloon;
      }
    }
    return strongestBloon;
  }

  /**
   * Assumption: should only be called IF known that there is a bloon in range OR ELSE will return null
   * Method should be used to find the first bloon in bloons collection within the range of the tower
   * @param bloonsCollection the bloon collection to search for first bloon
   * @return the first bloon
   */
  public Bloon findFirstBloon(BloonsCollection bloonsCollection) {
    GamePieceIterator<Bloon> iterator = bloonsCollection.createIterator();
    Bloon firstBloon = null;
    while (iterator.hasNext()) {
      Bloon bloon = iterator.next();
      if (ifCamoBloon(bloon)) {
        continue;
      }
      if (!bloon.isDead() && getDistance(bloon) <= getRadius()) {
        firstBloon = bloon;
        break;
      }
    }
    return firstBloon;
  }

  /**
   * Assumption: should only be called IF known that there is a bloon in range OR ELSE will return null
   * Method should be used to find the last bloon in bloons collection within the range of the tower
   * @param bloonsCollection the bloon collection to search for last bloon
   * @return the last bloon
   */
  public Bloon findLastBloon(BloonsCollection bloonsCollection) {
    GamePieceIterator<Bloon> iterator = bloonsCollection.createIterator();
    Bloon lastBloon = null;
    while (iterator.hasNext()) {
      Bloon bloon = iterator.next();
      if (ifCamoBloon(bloon)) {
        continue;
      }
      if (getDistance(bloon) <= getRadius()) {
        lastBloon = bloon;
      }
    }
    return lastBloon;
  }

  /**
   * Method should return the x velocity to shoot the target
   * @param target the bloon target
   * @return double - x velocity
   */
  public double findShootXVelocity(Bloon target) {
    double distance = getDistance(target);
    return (target.getXPosition() - this.getXPosition()) / distance * getShootingSpeed()
        / velocityAdjustment;
  }

  /**
   * Method should return the y velocity to shoot the target
   * @param target the bloon target
   * @return double - y velocity
   */
  public double findShootYVelocity(Bloon target) {
    double distance = getDistance(target);
    return (target.getYPosition() - this.getYPosition()) / distance * getShootingSpeed()
        / velocityAdjustment;
  }

  /**
   * Purpose: Method should be used to create projectiles and add to projectilesCollection
   * @param bloonsCollection: the collection to check if there is bloon in range
   * @param projectilesCollection: the collection to add new created projectiles
   * @return the Bloon to target at
   * @throws ConfigurationException
   */
  @Override
  public Bloon shoot(BloonsCollection bloonsCollection, ProjectilesCollection projectilesCollection)
      throws ConfigurationException {
    if (checkBalloonInRange(bloonsCollection)) {
      Bloon target = getTarget(bloonsCollection);
      ProjectileFactory projectileFactory = new SingleProjectileFactory();
      double projectileXVelocity = findShootXVelocity(target);
      double projectileYVelocity = findShootYVelocity(target);
      Projectile p = projectileFactory.createDart(getProjectileType(), this.getXPosition(),
          this.getYPosition(), projectileXVelocity, projectileYVelocity, findAngle(this, target));
      projectilesCollection.add(p);
      updateIfRestPeriod(true);
      return target;
    }

    return null;
  }

  /**
   * Purpose: Method should return the angle to position the tower to shoot the targetted bloon
   * @param tower
   * @param bloon
   * @return the angle to rotate tower
   */
  public double findAngle(Tower tower, Bloon bloon) {
    double angle = Math.toDegrees(
        Math.asin((bloon.getXPosition() - tower.getXPosition()) / tower.getDistance(bloon)));
    if (bloon.getYPosition() < tower.getYPosition()) {
      return angle;
    } else {
      return hundred_eight_degrees - angle;
    }
  }

}
