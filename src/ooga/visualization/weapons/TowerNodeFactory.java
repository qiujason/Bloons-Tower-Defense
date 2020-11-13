package ooga.visualization.weapons;

import javafx.scene.shape.Shape;
import ooga.backend.towers.TowerType;

public class TowerNodeFactory implements WeaponNodeFactory {

  @Override
  public TowerNode createTowerNode(TowerType towerType, double xPosition, double yPosition, double radius) {
    return new TowerNode(towerType, xPosition, yPosition, radius);
  }

}
