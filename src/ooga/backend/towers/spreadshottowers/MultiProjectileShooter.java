package ooga.backend.towers.spreadshottowers;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.projectile.factory.ProjectileFactory;
import ooga.backend.projectile.factory.SingleProjectileFactory;
import ooga.backend.towers.TowerType;

public class MultiProjectileShooter extends SpreadShotTower {

  private static final int numberOfShots = 8;
  private static final int degreeIncrementPerShot = 45;

  // TackShooter
  public MultiProjectileShooter(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
  }


  @Override
  public List<Projectile> shoot(BloonsCollection bloonsCollection) {
    List<Projectile> shot = new ArrayList<>();
    if(checkBalloonInRange(bloonsCollection)){
      for(int i = 0; i < numberOfShots; i++){
        ProjectileFactory projectileFactory = new SingleProjectileFactory();
        double projectileXVelocity = Math.cos(i*degreeIncrementPerShot);
        double projectileYVelocity = Math.sin(i*degreeIncrementPerShot);
        shot.add(projectileFactory
            .createDart(ProjectileType.SingleTargetProjectile, getXPosition(), getYPosition(), projectileXVelocity, projectileYVelocity));
      }
    }
    return shot;
  }

  @Override
  public TowerType getTowerType() {
    return TowerType.MultiProjectileShooter;
  }
}
