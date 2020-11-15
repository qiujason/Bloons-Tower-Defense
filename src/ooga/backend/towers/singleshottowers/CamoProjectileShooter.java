package ooga.backend.towers.singleshottowers;

import ooga.backend.projectile.ProjectileType;
import ooga.backend.towers.TowerType;

public class CamoProjectileShooter extends SingleShotTower{

  // ninja monkeys

  public CamoProjectileShooter(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
    setProjectileType(ProjectileType.CamoTargetProjectile);
  }

  @Override
  public TowerType getTowerType() {
    return TowerType.CamoProjectileShooter;
  }

}
