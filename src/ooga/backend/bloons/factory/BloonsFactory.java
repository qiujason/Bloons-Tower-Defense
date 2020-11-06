package ooga.backend.bloons.factory;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsType;

public interface BloonsFactory {
  Bloon createBloon(BloonsType bloonsType, int xPosition, int yPosition, int xVelocity, int yVelocity);
}
