package ooga.backend.API;

import java.util.List;
import ooga.backend.bloons.collection.BloonsCollection;
import ooga.backend.projectile.Projectile;

public interface TowersAPI {

  double getShootingSpeed();

  boolean checkBalloonInRange(BloonsCollection bloonsCollection);

  List<Projectile> shoot(BloonsCollection bloonsCollection);

  double getDistance(GamePiece target);

}
