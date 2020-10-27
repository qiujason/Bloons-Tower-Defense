public interface Bloon{

  /**
   * Performs the reaction of a bloon after it's been shot at
   */
  public void shotAt();

  /**
   * Performs the popping of the bloon (making new bloons if necessary)
   */
  public void popBloon();

  /** Update the x velocity
   * @param newVelocity to update the x velocity
   */
  public void setXVelocity(int newVelocity);

  /** Update the y velocity
   * @param newVelocity to update the y velocity
   */
  public void setYVelocity(int newVelocity);

  /** Update the number of hits left to pop the bloon
   * @param update the number of hits left
   */
  public void updateHitsLeft(int update);
}