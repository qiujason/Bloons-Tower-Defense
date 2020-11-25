/**
 * Class should be used to create MultiProjectileShooter to shoot 8 projectiles at a time when
 * here is a detected bloon in range
 * @author Annshine
 */
package ooga.backend.towers.spreadshottowers;

import ooga.backend.projectile.ProjectileType;
import ooga.backend.towers.TowerType;

public class MultiProjectileShooter extends SpreadShotTower {

  /**
   * Constructor for MultiProjectileShooter
   * @param myXPosition
   * @param myYPosition
   * @param myRadius
   * @param myShootingSpeed
   * @param myShootingRestRate
   */
  public MultiProjectileShooter(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
    setProjectileType(ProjectileType.SingleTargetProjectile);
  }

  /**
   * Method should be used to get tower type
   * @return TowerType.MultiProjectileShooter
   */
  @Override
  public TowerType getTowerType() {
    return TowerType.MultiProjectileShooter;
  }

}
