package ooga.backend.bloons.factory;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.types.BloonsType;

/**
 * interface that represents factory for producing bloons
 *
 * @author Jason Qiu
 */
public interface BloonsFactory {

  /**
   * creates a copy of a bloon
   * @param bloon bloon to be copied
   * @return copy of the bloon
   */
  Bloon createBloon(Bloon bloon);

  /**
   * creates a new bloon
   * @param bloonsType bloon type of new bloon
   * @param xPosition x position of new bloon
   * @param yPosition y position of new bloon
   * @param xVelocity x velocity of new bloon
   * @param yVelocity y velocity of new bloon
   * @return new bloon containing all input parameters in its initial state
   */
  Bloon createBloon(BloonsType bloonsType, double xPosition, double yPosition, double xVelocity,
      double yVelocity);

  /**
   * creates a bloon of the next bloon type of the input bloon
   * @param bloon bloon to get the next bloon of
   * @return the next bloon of bloon
   */
  Bloon createNextBloon(Bloon bloon);

}
