package ooga.backend.API;

import java.util.List;
import ooga.backend.bloons.Bloons;
import ooga.backend.darts.Dart;

public interface TowersAPI {

  double getShootingSpeed();

  boolean checkBalloonInRange(List<Bloons> bloonsList);

  List<Dart> shoot(List<Bloons> bloonsList);

  double getDistance(Bloons target);

}
