package ooga.backend.towers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TowerTypeTest {

  @Test
  void testName() {
    assertEquals("CamoProjectileShooter", TowerType.CamoProjectileShooter.name());
    assertEquals("SingleProjectileShooter", TowerType.SingleProjectileShooter.name());
    assertEquals("SuperSpeedProjectileShooter", TowerType.SuperSpeedProjectileShooter.name());
    assertEquals("UnlimitedRangeProjectileShooter", TowerType.UnlimitedRangeProjectileShooter.name());
    assertEquals("MultiProjectileShooter", TowerType.MultiProjectileShooter.name());
    assertEquals("MultiFrozenShooter", TowerType.MultiFrozenShooter.name());
  }

  @Test
  void testGetRadius() {
    assertEquals(3, TowerType.SingleProjectileShooter.getRadius());
  }

  @Test
  void getShootingRestRate() {
    assertEquals(1, TowerType.SuperSpeedProjectileShooter.getShootingRestRate());
  }

  @Test
  void getShootingSpeed() {
    assertEquals(15, TowerType.SuperSpeedProjectileShooter.getShootingSpeed());
  }

  @Test
  void isEnumName() {
    assertTrue(TowerType.isEnumName("SingleProjectileShooter"));
    assertFalse(TowerType.isEnumName("FakeName"));
  }

}