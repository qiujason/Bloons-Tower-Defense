package ooga.visualization.nodes;

import ooga.backend.bloons.types.BloonsType;

public class BloonNodeFactory {

  public BloonNode createBloonNode(BloonsType bloonsType, double xPosition, double yPosition, double radius) {
    return new BloonNode(bloonsType, xPosition, yPosition, radius);
  }


}
