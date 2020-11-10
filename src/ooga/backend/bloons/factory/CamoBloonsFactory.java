package ooga.backend.bloons.factory;

import ooga.backend.API.GamePiece;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.decorator.CamoDecorator;
import ooga.backend.bloons.types.BloonsType;

public class CamoBloonsFactory implements BloonsFactory {

  @Override
  public GamePiece createBloon(BloonsType bloonsType, double xPosition, double yPosition,
      double xVelocity, double yVelocity) {
    Bloon bloon = new Bloon(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
    return new CamoDecorator(bloon);
  }

}
