package ooga.backend.API;

import ooga.backend.GamePiece;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.projectile.ProjectilesCollection;

public interface TowersAPI {

  double getShootingSpeed();

  boolean checkBalloonInRange(BloonsCollection bloonsCollection);

  Bloon shoot(BloonsCollection bloonsCollection, ProjectilesCollection projectilesCollection);

  double getDistance(GamePiece target);

}
