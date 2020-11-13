package ooga.backend.bloons.factory;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.special.RegenBloon;
import ooga.backend.bloons.types.BloonsType;

public class RegenBloonsFactory implements BloonsFactory {

  @Override
  public Bloon createBloon(BloonsType bloonsType, double xPosition, double yPosition,
      double xVelocity, double yVelocity) {
    return new RegenBloon(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
  }

}
