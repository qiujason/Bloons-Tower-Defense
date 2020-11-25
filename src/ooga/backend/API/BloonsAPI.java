package ooga.backend.API;

import ooga.backend.ConfigurationException;
import ooga.backend.bloons.Bloon;

/**
 * API that allows for communication with Bloons in the backend
 *
 * @author Jason Qiu
 */
public interface BloonsAPI {

  /**
   * Update the x velocity
   *
   * @param newXVelocity to update the x velocity
   */
  void setXVelocity(double newXVelocity);

  /**
   * Update the y velocity
   *
   * @param newYVelocity to update the y velocity
   */
  void setYVelocity(double newYVelocity);

  /**
   * Spawns its next bloon types when shot at
   * @return an array of bloons that contains all the bloons to be spawned after bloon is hit
   * @throws ConfigurationException if a special bloon type is not recognized
   */
  Bloon[] shootBloon() throws ConfigurationException;

  /**
   * Sets the bloon to be dead
   */
  void setDead();

  /**
   * Checks if the bloon is dead
   * @return boolean determining if the bloon is dead
   */
  boolean isDead();

}
