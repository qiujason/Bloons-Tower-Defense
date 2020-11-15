package ooga.backend.towers.spreadshottowers;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.factory.ProjectileFactory;
import ooga.backend.projectile.factory.SingleProjectileFactory;
import ooga.backend.towers.Tower;

public abstract class SpreadShotTower extends Tower {

  private static final int numberOfShots = 8;
  private static final int degreeIncrementPerShot = 45;

  public SpreadShotTower(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
  }

  public int getNumberOfShots() {
    return numberOfShots;
  }

  public int getDegreeIncrementPerShot(){
    return degreeIncrementPerShot;
  }

  @Override
  public List<Projectile> shoot(BloonsCollection bloonsCollection) {
    updateCanShoot(false);
    List<Projectile> shot = new ArrayList<>();
    if(checkBalloonInRange(bloonsCollection)){
      for(int i = 0; i < getNumberOfShots(); i++){
        ProjectileFactory projectileFactory = new SingleProjectileFactory();
        double projectileXVelocity = Math.cos(i*getDegreeIncrementPerShot());
        double projectileYVelocity = Math.sin(i*getDegreeIncrementPerShot());
        shot.add(projectileFactory
            .createDart(getProjectileType(), getXPosition(), getYPosition(), projectileXVelocity, projectileYVelocity));
      }
    }
    return shot;
  }
}