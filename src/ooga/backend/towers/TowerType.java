/**
 * This enum represents the possible tower types with defined readius, shooting rest rate, shooting speed, and ifSingleShoot
 * @author Annshine
 */
package ooga.backend.towers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public enum TowerType {

  SingleProjectileShooter(3, 2, 3, true),
  MultiProjectileShooter(1.5, 3.5, 3, false),
  SpreadProjectileShooter(4, 3.5, 2, true),
  UnlimitedRangeProjectileShooter(20, 3, 4, true),
  SuperSpeedProjectileShooter(4, 1, 15, true),
  MultiFrozenShooter(1.5, 3.5, 3, false),
  CamoProjectileShooter(3, 2, 5, true);

  private final double radius;
  private final double shootingRestRate;
  private final double shootingSpeed;
  private final boolean ifSingleShot;

  /**
   * Constructor for Enum
   * @param radius
   * @param shootingRestRate
   * @param shootingSpeed
   * @param ifSingleShot
   */
  TowerType(double radius, double shootingRestRate, double shootingSpeed, boolean ifSingleShot) {
    this.radius = radius;
    this.shootingRestRate = shootingRestRate;
    this.shootingSpeed = shootingSpeed;
    this.ifSingleShot = ifSingleShot;
  }

  /**
   * Method should be used to return the radius
   * @return radius
   */
  public double getRadius() {
    return radius;
  }

  /**
   * Method should be used to return shooting rest rate
   * @return shootingRestRate
   */
  public double getShootingRestRate() {
    return shootingRestRate;
  }

  /**
   * Method should be used to return shooting speed
   * @return shootingSpeed
   */
  public double getShootingSpeed() {
    return shootingSpeed;
  }


  /**
   * @return either singleshottowers. + enum name or spreadshottowers. + enum name
   */
  @Override
  public String toString() {
    if (ifSingleShot) {
      return "singleshottowers." + super.toString();
    } else {
      return "spreadshottowers." + super.toString();
    }
  }

  private static final Map<String, TowerType> stringToEnum = new ConcurrentHashMap<>();

  static {
    for (TowerType type : values()) {
      stringToEnum.put(type.name(), type);
    }
  }

  /**
   * Method should be used to check if the key is a enum type
   * @param key - to check if it is an enum
   * @return boolean - represent if the key is an enum
   */
  public static boolean isEnumName(String key) {
    for (TowerType type : values()) {
      if (key.equals(type.name())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Method should be used to get the tower type from the name parameter
   * @param name
   * @return corresponding tower type
   */
  public static TowerType fromString(String name) {
    return stringToEnum.get(name);
  }
}
