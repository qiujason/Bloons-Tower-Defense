package ooga.backend.towers.spreadshottowers;

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