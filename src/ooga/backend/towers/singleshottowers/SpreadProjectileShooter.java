package ooga.backend.towers.singleshottowers;

import ooga.backend.projectile.ProjectileType;
import ooga.backend.towers.TowerType;

// CANON Tower
public class SpreadProjectileShooter extends SingleShotTower {

  public SpreadProjectileShooter(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
    setProjectileType(ProjectileType.SpreadProjectile);
  }

  @Override
  public TowerType getTowerType() {
    return TowerType.SpreadProjectileShooter;
  }
}
