package ooga.backend.bloons.factory;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.types.BloonsType;

public class BasicBloonsFactory implements BloonsFactory {

  @Override
  public Bloon createBloon(BloonsType bloonsType, double xPosition, double yPosition, double xVelocity, double yVelocity) {
    return new Bloon(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
  }

}
