package ooga.backend.towers.singleshottowers;

import static org.junit.jupiter.api.Assertions.*;

import ooga.backend.towers.TowerType;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import org.junit.jupiter.api.Test;

class SuperSpeedProjectileShooterTest {

  TowerFactory towerFactory = new SingleTowerFactory();
  SingleShotTower testTower = (SingleShotTower)
      towerFactory.createTower(TowerType.SuperSpeedProjectileShooter, 0,0);

  @Test
  void testGetTowerType() {
    assertEquals(TowerType.SuperSpeedProjectileShooter, testTower.getTowerType());
  }

  @Test
  void testGetRadius(){
    assertEquals(600, testTower.getRadius());
  }

  @Test
  void testGetShootingSpeed(){
    assertEquals(30, testTower.getShootingSpeed());
  }

  @Test
  void testGetRestingRate(){
    assertEquals(60, testTower.getShootingRestRate());
  }

}