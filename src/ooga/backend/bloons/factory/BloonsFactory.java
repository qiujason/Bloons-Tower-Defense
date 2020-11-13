package ooga.backend.bloons.factory;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;

public interface BloonsFactory {

  Bloon createBloon(BloonsTypeChain chain, BloonsType bloonsType, double xPosition, double yPosition, double xVelocity, double yVelocity);

}
