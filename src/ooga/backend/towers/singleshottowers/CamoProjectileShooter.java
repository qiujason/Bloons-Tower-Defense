/**
 * Class should be used to create CamoProjectileShooters to shoot detect all bloons (including Camo Bloons)
 * @author Annshine
 */
package ooga.backend.towers.singleshottowers;

import ooga.backend.projectile.ProjectileType;
import ooga.backend.towers.TowerType;

public class CamoProjectileShooter extends SingleShotTower {

  /**
   * Constructor for CamoProjectileShooter
   * @param myXPosition
   * @param myYPosition
   * @param myRadius
   * @param myShootingSpeed
   * @param myShootingRestRate
   */
  public CamoProjectileShooter(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
    setProjectileType(ProjectileType.CamoTargetProjectile);
  }

  /**
   * Method should be used to get tower type
   * @return TowerType.CamoProjectileShooter
   */
  @Override
  public TowerType getTowerType() {
    return TowerType.CamoProjectileShooter;
  }

}
