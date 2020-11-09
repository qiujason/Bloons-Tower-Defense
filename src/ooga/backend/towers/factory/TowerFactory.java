package ooga.backend.towers.factory;

import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;

public interface TowerFactory {
  Tower createTower(TowerType type, double xPosition, double yPosition);
}
