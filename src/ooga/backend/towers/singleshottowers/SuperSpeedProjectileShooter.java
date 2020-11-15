package ooga.backend.towers.singleshottowers;

import ooga.backend.towers.TowerType;

public class SuperSpeedProjectileShooter extends SingleShotTower{

  // super monkeys

  public SuperSpeedProjectileShooter(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
  }

  @Override
  public TowerType getTowerType() {
    return null;
//    return TowerType.SuperSpeedProjectileShooter;
  }
}
