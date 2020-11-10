package ooga.backend.bloons.factory;

import ooga.backend.API.GamePiece;
import ooga.backend.bloons.types.BloonsType;

public interface BloonsFactory {
  GamePiece createBloon(BloonsType bloonsType, double xPosition, double yPosition, double xVelocity, double yVelocity);
}
