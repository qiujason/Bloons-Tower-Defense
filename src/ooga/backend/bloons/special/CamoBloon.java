package ooga.backend.bloons.special;

import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.Specials;

public class CamoBloon extends SpecialBloon {

  public CamoBloon(BloonsType bloonsType, double xPosition, double yPosition, double xVelocity, double yVelocity) {
    super(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
    BloonsType newCamoType = new BloonsType(bloonsType.chain(), bloonsType.name(), bloonsType.RBE(), bloonsType.relativeSpeed(), Specials.Camo);
    setBloonsType(newCamoType);
  }

}
