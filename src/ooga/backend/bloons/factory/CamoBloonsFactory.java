package ooga.backend.bloons.factory;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.special.CamoBloon;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;

public class CamoBloonsFactory implements BloonsFactory {

  @Override
  public Bloon createBloon(BloonsTypeChain chain, BloonsType bloonsType, double xPosition, double yPosition,
      double xVelocity, double yVelocity) {
    return new CamoBloon(chain, bloonsType, xPosition, yPosition, xVelocity, yVelocity);
  }

}
