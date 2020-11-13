package ooga.backend.bloons.special;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.types.BloonsType;

public class CamoBloon extends Bloon {

  public CamoBloon(BloonsType bloonsType, double xPosition, double yPosition, double xVelocity, double yVelocity) {
    super(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
    setBloonsType(new BloonsType(bloonsType.name(), bloonsType.RBE(), bloonsType.relativeSpeed()));
  }

  @Override
  public BloonsType getBloonsType() {

    return null;
  }

}
