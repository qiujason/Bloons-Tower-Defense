/**
 * Class should be used to create UnlimitedRangeProjectileShooter to shoot and detect all bloons except Camo Bloons
 * This type of shooter has unlimited range
 * @author Annshine
 */
package ooga.backend.towers.singleshottowers;

import ooga.backend.towers.TowerType;

public class UnlimitedRangeProjectileShooter extends SingleShotTower {

  /**
   * Constructor for UnlimitedRangeProjectileShooter
   * @param myXPosition
   * @param myYPosition
   * @param myRadius
   * @param myShootingSpeed
   * @param myShootingRestRate
   */
  public UnlimitedRangeProjectileShooter(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
  }

  /**
   * Method should be used to get tower type
   * @return TowerType.UnlimitedRangeProjectileShooter
   */
  @Override
  public TowerType getTowerType() {
    return TowerType.UnlimitedRangeProjectileShooter;
  }
}
