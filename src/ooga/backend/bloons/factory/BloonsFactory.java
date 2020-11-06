package ooga.backend.bloons.factory;

import ooga.backend.bloons.Bloons;
import ooga.backend.bloons.BloonsType;

public interface BloonsFactory {
  Bloons createBloons(BloonsType bloonsType, int xPosition, int yPosition, int xVelocity, int yVelocity);
}
