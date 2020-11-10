package ooga.backend.towers.singleshottowers;

import ooga.backend.towers.TowerType;

public class SingleProjectileShooter extends SingleShotTower {

  // Dart monkeys
  public SingleProjectileShooter(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
  }

  @Override
  public TowerType getTowerType() {
    return TowerType.SingleProjectileShooter;
  }
}
