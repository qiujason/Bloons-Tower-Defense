package ooga.backend.API;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.towers.TowerType;

public interface BloonsAPI {

  /** Update the x velocity
   * @param newXVelocity to update the x velocity
   */
  void setXVelocity(double newXVelocity);

  /** Update the y velocity
   * @param newYVelocity to update the y velocity
   */
  void setYVelocity(double newYVelocity);

  Bloon[] shootBloon(BloonsTypeChain chain, TowerType towerCaller, int hits);

}
