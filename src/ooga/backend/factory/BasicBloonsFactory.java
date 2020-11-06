package ooga.backend.factory;

import ooga.backend.bloons.Bloons;

public class BasicBloonsFactory implements BloonsFactory {

  @Override
  public Bloons createBloons(int lives, int xPosition, int yPosition, int xVelocity, int yVelocity) {
    return new Bloons(lives, xPosition, yPosition, xVelocity, yVelocity);
  }
}
