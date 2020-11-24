package ooga.visualization.nodes;

import ooga.backend.towers.TowerType;

/**
 * TowerNodeFactory that implements in the WeaponNodeFactory interface. It creates the front
 * end TowerNode
 */
public class TowerNodeFactory implements WeaponNodeFactory {

  /**
   * @param tower the type of Tower that is being made
   * @param xPosition the x position of the TowerNode
   * @param yPosition the y position of the TowerNode
   * @param radius the radius of the TowerNode
   * @return the created front end TowerNode
   */
  @Override
  public TowerNode createTowerNode(TowerType tower, double xPosition, double yPosition,
      double radius) {
    return new TowerNode(tower, xPosition, yPosition, radius);
  }

}
