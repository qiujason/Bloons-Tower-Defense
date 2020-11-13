package ooga.backend.bloons.factory;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;

public class BasicBloonsFactory implements BloonsFactory {

  @Override
  public Bloon createBloon(BloonsTypeChain chain, BloonsType bloonsType, double xPosition, double yPosition, double xVelocity, double yVelocity) {
    return new Bloon(chain, bloonsType, xPosition, yPosition, xVelocity, yVelocity);
  }

}
