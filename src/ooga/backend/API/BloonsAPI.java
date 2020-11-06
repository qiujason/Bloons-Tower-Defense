package ooga.backend.API;

public interface BloonsAPI {

  /**
   * Performs the reaction of the object being shot at
   * @param hits the number of hits done to the object
   */
  void decrementLives(int hits);

  /** Update the number of lives left in the object
   * @param update the number of lives left
   */
  void updateLivesLeft(int update);

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

}
