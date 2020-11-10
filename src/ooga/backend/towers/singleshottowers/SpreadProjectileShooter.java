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
  }

  @Override
  public List<Projectile> shoot(BloonsCollection bloonsCollection) {
    List<Projectile> shot = new ArrayList<>();
    if(checkBalloonInRange(bloonsCollection)){
      Bloon target = getTarget(bloonsCollection);
      ProjectileFactory projectileFactory = new SingleProjectileFactory();
      double projectileXVelocity = findShootXVelocity(target);
      double projectileYVelocity = findShootYVelocity(target);
      shot.add(
          projectileFactory.createDart(ProjectileType.SpreadProjectile, getXPosition(),
              getYPosition(), projectileXVelocity, projectileYVelocity));
    }
    return shot;
  }

  @Override
  public TowerType getTowerType() {
    return TowerType.SpreadProjectileShooter;
  }
}
