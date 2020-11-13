package ooga.visualization.weapons;

import javafx.scene.shape.Shape;
import ooga.backend.towers.TowerType;

public class TowerNodeFactory implements WeaponNodeFactory {

  @Override
  public Shape createTowerNode(TowerType towerType, double xPosition, double yPosition, double radius) {
    return new TowerNode(xPosition, yPosition, radius, towerType);
  }

}
