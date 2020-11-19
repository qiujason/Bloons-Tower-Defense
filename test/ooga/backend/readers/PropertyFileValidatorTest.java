package ooga.backend.readers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import ooga.backend.bloons.types.Specials;
import org.junit.jupiter.api.Test;

class PropertyFileValidatorTest {

  @Test
  void testCheckIfValidReturnsTrue() {
    HashSet<String> requiredKeys = new HashSet<>();
    requiredKeys.add("SingleProjectileShooter");
    requiredKeys.add("MultiProjectileShooter");
    requiredKeys.add("SpreadProjectileShooter");
    requiredKeys.add("UnlimitedRangeProjectileShooter");
    requiredKeys.add("SuperSpeedProjectileShooter");
    requiredKeys.add("MultiFrozenShooter");
    requiredKeys.add("CamoProjectileShooter");
    PropertyFileValidator validator = new PropertyFileValidator("btd_towers/TowerMonkey.properties",
        requiredKeys);
    assertTrue(validator.containsNeededKeys());
    requiredKeys.add("impossibleKey");
    validator = new PropertyFileValidator("btd_towers/TowerMonkey.properties",
        requiredKeys);
    assertFalse(validator.containsNeededKeys());
  }

  @Test
  void testCheckIfValidReturnsFalse() {
    HashSet<String> requiredKeys = new HashSet<>();
    requiredKeys.add("SingleProjectileShooter");
    requiredKeys.add("MultiProjectileShooter");
    requiredKeys.add("SpreadProjectileShooter");
    requiredKeys.add("UnlimitedRangeProjectileShooter");
    requiredKeys.add("SuperSpeedProjectileShooter");
    requiredKeys.add("FrozenSpreadShooter");
    requiredKeys.add("CamoProjectileShooter");
    requiredKeys.add("impossibleKey");
    PropertyFileValidator validator = new PropertyFileValidator("btd_towers/TowerMonkey.properties",
        requiredKeys);
    assertFalse(validator.containsNeededKeys());
  }
}