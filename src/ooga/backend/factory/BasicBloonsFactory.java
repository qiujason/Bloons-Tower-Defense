package ooga.backend.factory;

import ooga.backend.API.Bloons;
import ooga.backend.bloons.BasicBloons;

public class BasicBloonsFactory implements BloonsFactory {

  @Override
  public Bloons createBloons() {
    return new BasicBloons();
  }
}
