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

  public SingleShotTower(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
    shootingChoice = defaultShootingChoice;
    setProjectileType(ProjectileType.SingleTargetProjectile);
  }

  public ShootingChoice getShootingChoice() {
    return shootingChoice;
  }

  public void updateShootingChoice(ShootingChoice newChoice) {
    shootingChoice = newChoice;
  }

  // should only be called IF known that there is a bloon in range OR ELSE will return null
  public Bloon getTarget(BloonsCollection bloonsCollection) {
    return switch (getShootingChoice()) {
      case StrongestBloon -> findStrongestBloon(bloonsCollection);
      case ClosestBloon -> findClosestBloon(bloonsCollection);
      case LastBloon -> findLastBloon(bloonsCollection);
      default -> findFirstBloon(bloonsCollection);
    };
  }

  // should only be called IF known that there is a bloon in range OR ELSE will return null
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

  // should only be called IF known that there is a bloon in range OR ELSE will return null
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

  // should only be called IF known that there is a bloon in range OR ELSE will return null
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

  // should only be called IF known that there is a bloon in range OR ELSE will return null
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

  public double findShootXVelocity(Bloon target) {
    double distance = getDistance(target);
    return (target.getXPosition() - this.getXPosition()) / distance * getShootingSpeed()
        / velocityAdjustment;
  }

  public double findShootYVelocity(Bloon target) {
    double distance = getDistance(target);
    return (target.getYPosition() - this.getYPosition()) / distance * getShootingSpeed()
        / velocityAdjustment;
  }

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
