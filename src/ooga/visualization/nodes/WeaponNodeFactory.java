package ooga.visualization.nodes;

import ooga.backend.towers.TowerType;

/**
 * This interface is used to create a front-end tower node.
 */
public interface WeaponNodeFactory {

  /**
   * Creates a TowerNode based on its type, the given coordinates, and the radius.
   * @param tower the type of Tower that is being made
   * @param xPosition the x position of the TowerNode
   * @param yPosition the y position of the TowerNode
   * @param radius the radius of the TowerNode
   * @return the created TowerNode
   */
  TowerNode createTowerNode(TowerType tower, double xPosition, double yPosition, double radius);

}