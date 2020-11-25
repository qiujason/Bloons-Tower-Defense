/**
 * Class should be used to create MultiFrozenShooter to shoot detect bloons and freeze them by shooting FreezeTargetProjectile
 * @author Annshine
 */

package ooga.backend.towers.spreadshottowers;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.towers.TowerType;

public class MultiFrozenShooter extends SpreadShotTower {

  /**
   * Constructor for MultiFrozenProjectile
   * @param myXPosition
   * @param myYPosition
   * @param myRadius
   * @param myShootingSpeed
   * @param myShootingRestRate
   */
  public MultiFrozenShooter(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
    setProjectileType(ProjectileType.FreezeTargetProjectile);
  }

  /**
   * Method should be used to get tower type
   * @return TowerType.MultiFrozenShooter
   */
  @Override
  public TowerType getTowerType() {
    return TowerType.MultiFrozenShooter;
  }

  /**
   * Method should be used to check if there is a bloon in range that is not dead, camo, and not frozen already
   * @param bloonsCollection
   * @return boolean to check whether if a bloon target is in range
   */
  @Override
  public boolean checkBalloonInRange(BloonsCollection bloonsCollection) {
    GamePieceIterator<Bloon> iterator = bloonsCollection.createIterator();
    while (iterator.hasNext()) {
      Bloon bloon = iterator.next();
      if (ifCamoBloon(bloon) || bloon.isDead() || bloon.isFreezeActive()) {
        continue;
      }
      double distance = getDistance(bloon);
      if (distance <= getRadius()) {
        return true;
      }
    }
    return false;
  }

}
