package ooga.backend.towers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public enum TowerType {

  SingleProjectileShooter(3, 3, 3, true),
  MultiProjectileShooter(1.5, 5, 3, false),
  SpreadProjectileShooter(4, 5, 2, true),
  UnlimitedRangeProjectileShooter(20, 3, 4, true),
  SuperSpeedProjectileShooter(4, 1, 15, true),
  FrozenSpreadShooter(1.5, 4, 3, false),
  CamoProjectileShooter(3, 3, 5, true);

  private double radius;
  private double shootingRestRate;
  private double shootingSpeed;
  private boolean ifSingleShot;

  TowerType(double radius) {
    this.radius = radius;
  }

  TowerType(double radius, double shootingRestRate) {
    this.radius = radius;
    this.shootingRestRate = shootingRestRate;
  }

  TowerType(double radius, double shootingRestRate, double shootingSpeed) {
    this.radius = radius;
    this.shootingRestRate = shootingRestRate;
    this.shootingSpeed = shootingSpeed;
  }

  TowerType(double radius, double shootingRestRate, double shootingSpeed, boolean ifSingleShot) {
    this.radius = radius;
    this.shootingRestRate = shootingRestRate;
    this.shootingSpeed = shootingSpeed;
    this.ifSingleShot = ifSingleShot;
  }

  public double getRadius() {
    return radius;
  }
  public double getShootingRestRate(){
    return shootingRestRate;
  }
  public double getShootingSpeed(){
    return shootingSpeed;
  }


  @Override
  public String toString() {
    if(ifSingleShot){
      return "singleshottowers." + super.toString();
    } else{
      return "spreadshottowers." + super.toString();
    }
  }

  private static final Map<String, TowerType> stringToEnum = new ConcurrentHashMap<>();
  static {
    for (TowerType type: values()){
      stringToEnum.put(type.name(), type);
    }
  }

  public static boolean isEnumName(String key){
    for (TowerType type: values()){
      if(key.equals(type.name())){
        return true;
      }
    }
    return false;
  }

  public static TowerType fromString(String name){
    return stringToEnum.get(name);
  }
}
