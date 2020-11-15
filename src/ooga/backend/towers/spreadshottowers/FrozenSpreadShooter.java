package ooga.backend.towers.spreadshottowers;

import ooga.backend.projectile.ProjectileType;
import ooga.backend.towers.TowerType;

public class FrozenSpreadShooter extends SpreadShotTower {

  // Ice monkey
  public FrozenSpreadShooter(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
    setProjectileType(ProjectileType.FreezeTargetProjectile);
  }

  @Override
  public TowerType getTowerType() {
    return TowerType.FrozenSpreadShooter;
  }

}
