package ooga.backend.factory;

import ooga.backend.bloons.Bloons;
import ooga.backend.bloons.BasicBloons;

public class BasicBloonsFactory implements BloonsFactory {

  @Override
  public Bloons createBloons(int lives, int xPosition, int yPosition, int xVelocity, int yVelocity) {
    return new BasicBloons(lives, xPosition, yPosition, xVelocity, yVelocity);
  }
}
