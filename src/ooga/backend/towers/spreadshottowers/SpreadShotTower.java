/**
 * This class is an abstract class that should be extended to create spread shot towers
 * SpreadShotTowers fire MULTIPLE projectiles at a time
 * @author Annshine
 */
package ooga.backend.towers.spreadshottowers;

import ooga.backend.ConfigurationException;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.projectile.factory.ProjectileFactory;
import ooga.backend.projectile.factory.SingleProjectileFactory;
import ooga.backend.towers.Tower;

public abstract class SpreadShotTower extends Tower {

  private static final int numberOfShots = 8;
  private static final int degreeIncrementPerShot = 45;

  /**
   * Constructor for SpreadShotTowers
   * @param myXPosition
   * @param myYPosition
   * @param myRadius
   * @param myShootingSpeed
   * @param myShootingRestRate
   */
  public SpreadShotTower(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
  }

  /**
   * The number of projectiles the tower is shooting
   * @return numberOfShots
   */
  public int getNumberOfShots() {
    return numberOfShots;
  }

  private int getDegreeIncrementPerShot() {
    return degreeIncrementPerShot;
  }

  /**
   * Method should add multiple projectiles to the projectiles collection
   * @param bloonsCollection: the collection to check if there is bloon in range
   * @param projectilesCollection: the collection to add new created projectiles
   * @return the Bloon target
   * @throws ConfigurationException
   */
  @Override
  public Bloon shoot(BloonsCollection bloonsCollection, ProjectilesCollection projectilesCollection)
      throws ConfigurationException {
    if (checkBalloonInRange(bloonsCollection)) {
      for (int i = 0; i < getNumberOfShots(); i++) {
        ProjectileFactory projectileFactory = new SingleProjectileFactory();
        double projectileXVelocity = Math.cos(i * getDegreeIncrementPerShot());
        double projectileYVelocity = Math.sin(i * getDegreeIncrementPerShot());
        projectilesCollection.add(projectileFactory
            .createDart(getProjectileType(), getXPosition(), getYPosition(), projectileXVelocity,
                projectileYVelocity, 0));
        updateIfRestPeriod(true);
      }
    }
    return null;
  }
}