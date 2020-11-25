package ooga.backend.bloons.factory;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.types.BloonsType;

/**
 * Factory class that produces basic (non-special) bloons
 *
 * @author Jason Qiu
 */
public class BasicBloonsFactory implements BloonsFactory {

  /**
   * creates a copy of a basic bloon
   * @param bloon basic bloon to be copied
   * @return copy of the basic bloon
   */
  @Override
  public Bloon createBloon(Bloon bloon) {
    Bloon newBloon = createBloon(bloon.getBloonsType(), bloon.getXPosition(), bloon.getYPosition(),
        bloon.getXVelocity(), bloon.getYVelocity());
    newBloon.setDistanceTraveled(bloon.getDistanceTraveled());
    return newBloon;
  }

  /**
   * creates a new basic bloon
   * @param bloonsType bloon type of new bloon
   * @param xPosition x position of new bloon
   * @param yPosition y position of new bloon
   * @param xVelocity x velocity of new bloon
   * @param yVelocity y velocity of new bloon
   * @return new basic bloon containing all input parameters in its initial state
   */
  @Override
  public Bloon createBloon(BloonsType bloonsType, double xPosition, double yPosition,
      double xVelocity, double yVelocity) {
    return new Bloon(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
  }

  /**
   * creates a basic bloon of the next bloon type of the input bloon
   * @param bloon basic bloon to get the next bloon of
   * @return the next basic bloon of bloon
   */
  @Override
  public Bloon createNextBloon(Bloon bloon) {
    Bloon newBloon = createBloon(
        bloon.getBloonsType().chain().getNextBloonsType(bloon.getBloonsType()),
        bloon.getXPosition(), bloon.getYPosition(), bloon.getXVelocity(), bloon.getYVelocity());
    newBloon.setDistanceTraveled(bloon.getDistanceTraveled());
    return newBloon;
  }

}
