package ooga.backend.API;

import ooga.backend.ConfigurationException;
import ooga.backend.GamePiece;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.projectile.ProjectilesCollection;

public interface TowersAPI {

  double getShootingSpeed();

  boolean checkBalloonInRange(BloonsCollection bloonsCollection);

  Bloon shoot(BloonsCollection bloonsCollection, ProjectilesCollection projectilesCollection)
      throws ConfigurationException;

  double getDistance(GamePiece target);

}
