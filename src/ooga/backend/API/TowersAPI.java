package ooga.backend.API;

import java.util.List;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.darts.Dart;

public interface TowersAPI {

  double getShootingSpeed();

  boolean checkBalloonInRange(BloonsCollection bloonsCollection);

  List<Dart> shoot(BloonsCollection bloonsCollection);

  double getDistance(GamePiece target);

}
