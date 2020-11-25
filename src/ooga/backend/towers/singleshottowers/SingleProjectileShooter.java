/**
 * Class should be used to create SingleProjectileShooter to shoot detect all bloons except Camo Bloons
 * @author Annshine
 */
package ooga.backend.towers.singleshottowers;

import ooga.backend.towers.TowerType;

public class SingleProjectileShooter extends SingleShotTower {

  /**
   * Constructor for SingleProjectileShooter
   * @param myXPosition
   * @param myYPosition
   * @param myRadius
   * @param myShootingSpeed
   * @param myShootingRestRate
   */
  public SingleProjectileShooter(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
  }

  /**
   * Method should be used to get tower type
   * @return TowerType.SingleProjectileShooter
   */
  @Override
  public TowerType getTowerType() {
    return TowerType.SingleProjectileShooter;
  }
}
