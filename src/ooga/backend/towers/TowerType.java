package ooga.backend.towers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum TowerType {

  SingleProjectileShooter(3, 5, 3, true),
  MultiProjectileShooter(50, 10, 10, false),
  SpreadProjectileShooter(75, 20, 20, true),
  UnlimitedRangeProjectileShooter(150, 5, 10, true),
  SuperSpeedProjectileShooter(100, 1, 30, true),
  FrozenSpreadShooter(15, 10, 20, false),
  CamoProjectileShooter(75, 5, 15, true);

  private double radius;
  private double shootingRestRate;
  private double shootingSpeed;
  private boolean ifSingleShot;

  TowerType(int radius) {
    this.radius = radius;
  }

  TowerType(int radius, int shootingRestRate) {
    this.radius = radius;
    this.shootingRestRate = shootingRestRate;
  }

  TowerType(int radius, int shootingRestRate, int shootingSpeed) {
    this.radius = radius;
    this.shootingRestRate = shootingRestRate;
    this.shootingSpeed = shootingSpeed;
  }

  TowerType(int radius, int shootingRestRate, int shootingSpeed, boolean ifSingleShot) {
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
