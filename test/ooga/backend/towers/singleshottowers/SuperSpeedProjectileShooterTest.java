package ooga.backend.towers.singleshottowers;

import static org.junit.jupiter.api.Assertions.*;

import ooga.backend.ConfigurationException;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.UpgradeChoice;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SuperSpeedProjectileShooterTest {

  TowerFactory towerFactory;
  SuperSpeedProjectileShooter testTower;

  SuperSpeedProjectileShooterTest() throws ConfigurationException {
    towerFactory = new SingleTowerFactory();
    testTower = (SuperSpeedProjectileShooter)
        towerFactory.createTower(TowerType.SuperSpeedProjectileShooter, 0,0);
  }

  @Test
  void testGetTowerType() {
    assertEquals(TowerType.SuperSpeedProjectileShooter, testTower.getTowerType());
  }

  @Test
  void testGetRadius(){
    assertEquals(4, testTower.getRadius());
  }

  @Test
  void testGetShootingSpeed(){
    assertEquals(15, testTower.getShootingSpeed());
  }

  @Test
  void testGetRestingRate(){
    assertEquals(60, testTower.getShootingRestRate());
  }

  @Test
  void testUpgradeRadius(){
    testTower.performUpgrade(UpgradeChoice.RadiusUpgrade);
    assertEquals(4.2, testTower.getRadius());
  }

  @Test
  void testUpgradeShootingSpeed(){
    testTower.performUpgrade(UpgradeChoice.ShootingSpeedUpgrade);
    assertEquals(15.75, testTower.getShootingSpeed());
  }

  @Test
  void testUpgradeRestRate(){
    testTower.performUpgrade(UpgradeChoice.ShootingRestRateUpgrade);
    assertEquals(60/1.05, testTower.getShootingRestRate());
  }
}