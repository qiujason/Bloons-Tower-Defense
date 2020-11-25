/**
 * Class should be used to create SuperSpeedProjectileShooter to shoot and detect all bloons except Camo Bloons
 * This class should fire projectiles at a much higher speed + larger range
 * @author Annshine
 */
package ooga.backend.towers.singleshottowers;

import ooga.backend.towers.TowerType;

public class SuperSpeedProjectileShooter extends SingleShotTower {

  /**
   * Constructor for SuperSpeedProjectileShooter
   * @param myXPosition
   * @param myYPosition
   * @param myRadius
   * @param myShootingSpeed
   * @param myShootingRestRate
   */
  public SuperSpeedProjectileShooter(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
  }

  /**
   * Method should be used to get tower type
   * @return TowerType.SuperSpeedProjectileShooter
   */
  @Override
  public TowerType getTowerType() {
    return TowerType.SuperSpeedProjectileShooter;
  }
}
