package ooga.backend.towers.spreadshottowers;

import ooga.backend.projectile.ProjectileType;
import ooga.backend.towers.TowerType;

public class MultiProjectileShooter extends SpreadShotTower {

  // TackShooter
  public MultiProjectileShooter(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
    setProjectileType(ProjectileType.SingleTargetProjectile);
  }

  @Override
  public TowerType getTowerType() {
    return TowerType.MultiProjectileShooter;
  }

}
