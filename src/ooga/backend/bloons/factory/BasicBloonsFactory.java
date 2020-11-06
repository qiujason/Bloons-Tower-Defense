package ooga.backend.bloons.factory;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsType;

public class BasicBloonsFactory implements BloonsFactory {

  @Override
  public Bloon createBloon(BloonsType bloonsType, int xPosition, int yPosition, int xVelocity, int yVelocity) {
    return new Bloon(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
  }
}
