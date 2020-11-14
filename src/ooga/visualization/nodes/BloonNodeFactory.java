package ooga.visualization.nodes;

import ooga.backend.bloons.types.BloonsType;

public class BloonNodeFactory {

  public BloonNode createBloonNode(double xPosition, double yPosition, double radius) {
    return new BloonNode(xPosition, yPosition, radius);
  }


}
