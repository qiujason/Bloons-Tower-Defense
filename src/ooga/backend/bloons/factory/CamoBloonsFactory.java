package ooga.backend.bloons.factory;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.special.CamoBloon;
import ooga.backend.bloons.types.BloonsType;

/**
 * Factory class that produces camo bloons
 *
 * @author Jason Qiu
 */
public class CamoBloonsFactory implements BloonsFactory {

  /**
   * creates a copy of bloon
   * @param bloon bloon to be copied
   * @return copy of the bloon that has camo properties
   */
  @Override
  public Bloon createBloon(Bloon bloon) {
    Bloon newCamoBloon = createBloon(bloon.getBloonsType(), bloon.getXPosition(),
        bloon.getYPosition(), bloon.getXVelocity(), bloon.getYVelocity());
    newCamoBloon.setDistanceTraveled(bloon.getDistanceTraveled());
    return newCamoBloon;
  }

  /**
   * creates a new camo bloon
   * @param bloonsType bloon type of new bloon
   * @param xPosition x position of new bloon
   * @param yPosition y position of new bloon
   * @param xVelocity x velocity of new bloon
   * @param yVelocity y velocity of new bloon
   * @return new camo bloon containing all input parameters in its initial state
   */
  @Override
  public Bloon createBloon(BloonsType bloonsType, double xPosition, double yPosition,
      double xVelocity, double yVelocity) {
    return new CamoBloon(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
  }

  /**
   * creates a camo bloon of the next bloon type of the input bloon
   * @param bloon camo bloon to get the next bloon of
   * @return the next camo bloon of bloon
   */
  @Override
  public Bloon createNextBloon(Bloon bloon) {
    Bloon newCamoBloon = createBloon(
        bloon.getBloonsType().chain().getNextBloonsType(bloon.getBloonsType()),
        bloon.getXPosition(), bloon.getYPosition(),
        bloon.getXVelocity(), bloon.getYVelocity());
    newCamoBloon.setDistanceTraveled(bloon.getDistanceTraveled());
    return newCamoBloon;
  }
}
