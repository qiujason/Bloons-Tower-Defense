package ooga.backend.projectile;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SpreadProjectileTest {

  @Test
  void testGetType() {
    Projectile dart = new SpreadProjectile(ProjectileType.SpreadProjectile, 10,15,-5,5,0);
    assertEquals(ProjectileType.SpreadProjectile, dart.getType());
  }

  @Test
  void testGetRadius() {
    Projectile dart = new SpreadProjectile(ProjectileType.SpreadProjectile, 10,15,-5,5,0);
    assertEquals(10, dart.getRadius());
  }
}