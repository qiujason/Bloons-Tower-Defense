package ooga.backend.bloons.special;

import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.Specials;

/**
 * represents a camo bloon that is invisible to certain towers
 *
 * @author Jason Qiu
 */
public class CamoBloon extends SpecialBloon {

  /**
   * creates a camo bloon
   * @param bloonsType bloon type of new bloon
   * @param xPosition x position of new bloon
   * @param yPosition y position of new bloon
   * @param xVelocity x velocity of new bloon
   * @param yVelocity y velocity of new bloon
   */
  public CamoBloon(BloonsType bloonsType, double xPosition, double yPosition, double xVelocity,
      double yVelocity) {
    super(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
    BloonsType newCamoType = new BloonsType(bloonsType.chain(), bloonsType.name(), bloonsType.RBE(),
        bloonsType.relativeSpeed(), Specials.Camo);
    setBloonsType(newCamoType);
  }

}
