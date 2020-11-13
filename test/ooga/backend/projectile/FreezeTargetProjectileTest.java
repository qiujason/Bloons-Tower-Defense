package ooga.backend.projectile;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FreezeTargetProjectileTest {

  @Test
  void testGetType() {
    Projectile dart = new FreezeTargetProjectile(ProjectileType.FreezeTargetProjectile, 10,15,-5,5);
    assertEquals(ProjectileType.FreezeTargetProjectile, dart.getType());
  }
}