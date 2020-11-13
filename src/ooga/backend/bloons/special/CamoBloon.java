package ooga.backend.bloons.special;

import java.util.HashSet;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.bloons.types.Specials;

public class CamoBloon extends Bloon {

  public CamoBloon(BloonsTypeChain chain, BloonsType bloonsType, double xPosition, double yPosition, double xVelocity, double yVelocity) {
    super(chain, bloonsType, xPosition, yPosition, xVelocity, yVelocity);
    BloonsType newCamoType = new BloonsType(bloonsType.name(), bloonsType.RBE(), bloonsType.relativeSpeed(), new HashSet<>());
    newCamoType.specials().add(Specials.CAMO);
    setBloonsType(newCamoType);
  }

}
