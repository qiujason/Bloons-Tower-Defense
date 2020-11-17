package ooga.backend.bloons.factory;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.special.RegenBloon;
import ooga.backend.bloons.types.BloonsType;

public class RegenBloonsFactory implements BloonsFactory {

  @Override
  public Bloon createBloon(Bloon bloon) {
    return createBloon(bloon.getBloonsType(), bloon.getXPosition(), bloon.getYPosition(), bloon.getXVelocity(), bloon.getYVelocity());
  }

  @Override
  public Bloon createBloon(BloonsType bloonsType, double xPosition, double yPosition,
      double xVelocity, double yVelocity) {
    return new RegenBloon(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
  }

  public Bloon createBloon(BloonsType bloonsType, BloonsType originalType, double xPosition, double yPosition,
      double xVelocity, double yVelocity) {
    return new RegenBloon(bloonsType, originalType, xPosition, yPosition, xVelocity, yVelocity);
  }

  @Override
  public Bloon createNextBloon(Bloon bloon) {
    if (bloon instanceof RegenBloon regenBloon) {
      return createBloon(regenBloon.getBloonsType().chain().getNextBloonsType(
          regenBloon.getBloonsType()), regenBloon.getOriginalType(), regenBloon.getXPosition(),
          regenBloon.getYPosition(), regenBloon.getXVelocity(), regenBloon.getYVelocity());
    }
    return createBloon(bloon.getBloonsType().chain().getNextBloonsType(bloon.getBloonsType()),
        bloon.getXPosition(), bloon.getYPosition(), bloon.getXVelocity(), bloon.getYVelocity());
  }

}
