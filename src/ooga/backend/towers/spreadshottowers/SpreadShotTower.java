package ooga.backend.towers.spreadshottowers;

import ooga.backend.towers.Tower;

public abstract class SpreadShotTower extends Tower {

  public SpreadShotTower(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
  }

}