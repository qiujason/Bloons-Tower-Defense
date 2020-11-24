package ooga.backend.bloons.factory;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.types.BloonsType;

public class BasicBloonsFactory implements BloonsFactory {

  @Override
  public Bloon createBloon(Bloon bloon) {
    Bloon newBloon = createBloon(bloon.getBloonsType(), bloon.getXPosition(), bloon.getYPosition(),
        bloon.getXVelocity(), bloon.getYVelocity());
    newBloon.setDistanceTraveled(bloon.getDistanceTraveled());
    return newBloon;
  }

  @Override
  public Bloon createBloon(BloonsType bloonsType, double xPosition, double yPosition,
      double xVelocity, double yVelocity) {
    return new Bloon(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
  }

  @Override
  public Bloon createNextBloon(Bloon bloon) {
    Bloon newBloon = createBloon(
        bloon.getBloonsType().chain().getNextBloonsType(bloon.getBloonsType()),
        bloon.getXPosition(), bloon.getYPosition(), bloon.getXVelocity(), bloon.getYVelocity());
    newBloon.setDistanceTraveled(bloon.getDistanceTraveled());
    return newBloon;
  }

}
