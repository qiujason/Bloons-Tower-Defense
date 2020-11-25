package ooga.backend.bloons.factory;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.special.RegenBloon;
import ooga.backend.bloons.types.BloonsType;

/**
 * Factory class that produces camo bloons
 *
 * @author Jason Qiu
 */
public class RegenBloonsFactory implements BloonsFactory {

  /**
   * creates a regen copy of a bloon
   * @param bloon bloon to be copied
   * @return copy of the bloon that has regen properties
   */
  @Override
  public Bloon createBloon(Bloon bloon) {
    Bloon newRegenBloon = createBloon(bloon.getBloonsType(), bloon.getXPosition(),
        bloon.getYPosition(), bloon.getXVelocity(), bloon.getYVelocity());
    newRegenBloon.setDistanceTraveled(bloon.getDistanceTraveled());
    return newRegenBloon;
  }

  /**
   * creates a new regen bloon
   * @param bloonsType bloon type of new bloon
   * @param xPosition x position of new bloon
   * @param yPosition y position of new bloon
   * @param xVelocity x velocity of new bloon
   * @param yVelocity y velocity of new bloon
   * @return new regen bloon containing all input parameters in its initial state
   */
  @Override
  public Bloon createBloon(BloonsType bloonsType, double xPosition, double yPosition,
      double xVelocity, double yVelocity) {
    RegenBloon bloon = new RegenBloon(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
    return bloon;
  }

  /**
   * creates a new regen bloon
   * @param bloonsType bloon type of new bloon
   * @param originalType original bloon type that represents the max bloon type that the regen bloon can
   *                     regen to
   * @param xPosition x position of new bloon
   * @param yPosition y position of new bloon
   * @param xVelocity x velocity of new bloon
   * @param yVelocity y velocity of new bloon
   * @return new regen bloon containing all input parameters in its initial state
   */
  public Bloon createBloon(BloonsType bloonsType, BloonsType originalType, double xPosition,
      double yPosition,
      double xVelocity, double yVelocity) {
    return new RegenBloon(bloonsType, originalType, xPosition, yPosition, xVelocity, yVelocity);
  }

  /**
   * creates a regen bloon of the next bloon type of the input bloon
   * @param bloon regen bloon to get the next bloon of
   * @return the next regen bloon of bloon
   */
  @Override
  public Bloon createNextBloon(Bloon bloon) {
    Bloon newRegenBloon;
    if (bloon instanceof RegenBloon regenBloon) {
      newRegenBloon = createBloon(regenBloon.getBloonsType().chain().getNextBloonsType(
          regenBloon.getBloonsType()), regenBloon.getOriginalType(), regenBloon.getXPosition(),
          regenBloon.getYPosition(), regenBloon.getXVelocity(), regenBloon.getYVelocity());
    } else {
      newRegenBloon = createBloon(
          bloon.getBloonsType().chain().getNextBloonsType(bloon.getBloonsType()),
          bloon.getXPosition(), bloon.getYPosition(), bloon.getXVelocity(), bloon.getYVelocity());
    }
    newRegenBloon.setDistanceTraveled(bloon.getDistanceTraveled());
    return newRegenBloon;
  }

}
