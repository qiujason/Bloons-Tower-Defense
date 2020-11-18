package ooga.backend.bloons.factory;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.types.BloonsType;

public interface BloonsFactory {

  Bloon createBloon(Bloon bloon);

  Bloon createBloon(BloonsType bloonsType, double xPosition, double yPosition, double xVelocity, double yVelocity);

  Bloon createNextBloon(Bloon bloon);

}
