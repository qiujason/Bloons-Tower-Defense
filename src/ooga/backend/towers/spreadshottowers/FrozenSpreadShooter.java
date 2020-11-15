package ooga.backend.towers.spreadshottowers;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.projectile.factory.ProjectileFactory;
import ooga.backend.projectile.factory.SingleProjectileFactory;
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
    return null;
//    return TowerType.FrozenSpreadShooter;
  }

  @Override
  public ProjectileType getProjectileType(){
    return ProjectileType.FreezeTargetProjectile;
  }
}
