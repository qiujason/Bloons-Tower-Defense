package ooga.backend.towers.spreadshottowers;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.towers.TowerType;

public class MultiFrozenShooter extends SpreadShotTower {

  // Ice monkey
  public MultiFrozenShooter(double myXPosition, double myYPosition, double myRadius,
      double myShootingSpeed, double myShootingRestRate) {
    super(myXPosition, myYPosition, myRadius, myShootingSpeed, myShootingRestRate);
    setProjectileType(ProjectileType.FreezeTargetProjectile);
  }

  @Override
  public TowerType getTowerType() {
    return TowerType.MultiFrozenShooter;
  }

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
