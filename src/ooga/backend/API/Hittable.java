package ooga.backend.API;

public interface Hittable {

  /**
   * Performs the reaction of the object being shot at
   * @param hits the number of hits done to the object
   */
  void decrementLives(int hits);

  /** Update the number of lives left in the object
   * @param update the number of lives left
   */
  void updateLivesLeft(int update);

}
