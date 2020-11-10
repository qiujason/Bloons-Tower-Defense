package ooga.backend.towers.singleshottowers;

import ooga.backend.towers.TowerType;
import ooga.backend.towers.singleshottowers.SingleShotTower;

public class UnlimitedRangeProjectileShooter extends SingleShotTower {

  // Sniper Monkeys
  public UnlimitedRangeProjectileShooter(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
  }

  @Override
  public TowerType getTowerType() {
    return TowerType.UnlimitedRangeProjectileShooter;
  }
}
