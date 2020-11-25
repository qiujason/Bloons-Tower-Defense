package ooga.visualization.menu;

/**
 * Interface is used to create a weapon description such as name, cost, and short description for
 * each of the in game Tower and Road Item buttons.
 */
public interface Describable {

  /**
   * Sets the WeaponDescription to the button based on its weapon type.
   *
   * @param weaponType holds the WeaponType(String value of TowerType or RoadItemType)
   */
  void setWeaponDescription(String weaponType);

  /**
   * @return the WeaponDescription
   */
  WeaponDescription getWeaponDescription();

}
