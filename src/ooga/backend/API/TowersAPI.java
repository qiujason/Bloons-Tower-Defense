package ooga.backend.API;

import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.darts.Dart;

public interface TowersAPI {

  double getShootingSpeed();

  boolean checkBalloonInRange(List<Bloon> bloonsList);

  List<Dart> shoot(List<Bloon> bloonsList);

  double getDistance(Bloon target);

}
