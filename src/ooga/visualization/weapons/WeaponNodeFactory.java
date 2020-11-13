package ooga.visualization.weapons;

import javafx.scene.shape.Shape;
import ooga.backend.towers.TowerType;

public interface WeaponNodeFactory {

  Shape createTowerNode(TowerType towerType, double xPosition, double yPosition, double radius);

}