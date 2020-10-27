public interface Tower{

  /** Shoot at target following the specific tower's shooting action
   */
  public void shootAtTarget();

  /**
   * @param newRadius: the new radius to update the radius of the tower
   */
  public void setRadius(int newRadius);

  /**
   * @return the value of a tower (amount of money it costs)
   */
  public int getValue();

  /**
   * To activate a power up for the tower that can be bought
   */
  public void activatePowerUp();
}