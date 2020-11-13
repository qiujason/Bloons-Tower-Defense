package ooga.backend.towers.spreadshottowers;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.bloons.collection.BloonsCollection;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;
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
}