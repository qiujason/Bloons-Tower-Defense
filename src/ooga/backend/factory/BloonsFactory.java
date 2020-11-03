package ooga.backend.factory;

import ooga.backend.bloons.Bloons;

public interface BloonsFactory {
  Bloons createBloons(int lives, int xPosition, int yPosition, int xVelocity, int yVelocity);
}
