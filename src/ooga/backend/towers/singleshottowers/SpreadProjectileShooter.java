/**
 * Class should be used to create SpreadProjectileShooter to shoot and detect all bloons except Camo Bloons
 * This tower should fire SpreadProjectile's instead of regular projectiles
 * @author Annshine
 */
package ooga.backend.towers.singleshottowers;

import ooga.backend.projectile.ProjectileType;
import ooga.backend.towers.TowerType;

// CANON Tower
public class SpreadProjectileShooter extends SingleShotTower {

  /**
   * Constructor for SpreadProjectileShooter
   * @param myXPosition
   * @param myYPosition
   * @param myRadius
   * @param myShootingSpeed
   * @param myShootingRestRate
   */
  public SpreadProjectileShooter(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
    setProjectileType(ProjectileType.SpreadProjectile);
  }

  /**
   * Method should be used to get tower type
   * @return TowerType.SpreadProjectileShooter
   */
  @Override
  public TowerType getTowerType() {
    return TowerType.SpreadProjectileShooter;
  }
}
