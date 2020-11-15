package ooga.backend.towers.singleshottowers;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.projectile.factory.ProjectileFactory;
import ooga.backend.projectile.factory.SingleProjectileFactory;
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
