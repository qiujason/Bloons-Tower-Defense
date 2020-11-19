package ooga.backend.bloons.factory;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.special.CamoBloon;
import ooga.backend.bloons.types.BloonsType;

public class CamoBloonsFactory implements BloonsFactory {

  @Override
  public Bloon createBloon(Bloon bloon) {
    Bloon newCamoBloon = createBloon(bloon.getBloonsType(), bloon.getXPosition(), bloon.getYPosition(), bloon.getXVelocity(), bloon.getYVelocity());
    newCamoBloon.setDistanceTraveled(bloon.getDistanceTraveled());
    return newCamoBloon;
  }

  @Override
  public Bloon createBloon(BloonsType bloonsType, double xPosition, double yPosition,
      double xVelocity, double yVelocity) {
    return new CamoBloon(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
  }

  @Override
  public Bloon createNextBloon(Bloon bloon) {
    Bloon newCamoBloon = createBloon(bloon.getBloonsType().chain().getNextBloonsType(bloon.getBloonsType()), bloon.getXPosition(), bloon.getYPosition(),
        bloon.getXVelocity(), bloon.getYVelocity());
    newCamoBloon.setDistanceTraveled(bloon.getDistanceTraveled());
    return newCamoBloon;
  }
}
