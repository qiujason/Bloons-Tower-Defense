package ooga.backend.API;

public interface Movable {

  /**
   * Update the position of the object
   */
  void updatePosition();

  /** Update the x velocity
   * @param newXVelocity to update the x velocity
   */
  void setXVelocity(int newXVelocity);

  /** Update the y velocity
   * @param newYVelocity to update the y velocity
   */
  void setYVelocity(int newYVelocity);

  int getXPosition();

  int getYPosition();

}
