package ooga.backend.bloons.factory;

import ooga.backend.bloons.Bloons;
import ooga.backend.bloons.BloonsType;

public class BasicBloonsFactory implements BloonsFactory {

  @Override
  public Bloons createBloons(BloonsType bloonsType, int xPosition, int yPosition, int xVelocity, int yVelocity) {
    return new Bloons(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
  }
}
