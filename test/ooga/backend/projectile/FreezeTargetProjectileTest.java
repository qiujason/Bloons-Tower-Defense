package ooga.backend.projectile;

import static org.junit.jupiter.api.Assertions.*;

import ooga.backend.projectile.types.FreezeTargetProjectile;
import org.junit.jupiter.api.Test;

class FreezeTargetProjectileTest {

  @Test
  void testGetType() {
    Projectile dart = new FreezeTargetProjectile(ProjectileType.FreezeTargetProjectile, 10,15,-5,5,0);
    assertEquals(ProjectileType.FreezeTargetProjectile, dart.getType());
  }

  @Test
  void testUpdateAndUpdatePosition() {
    Projectile dart = new FreezeTargetProjectile(ProjectileType.FreezeTargetProjectile, 10,15,-5,5,0);
    dart.update();
    assertEquals(5, dart.getXPosition());
    assertEquals(20, dart.getYPosition());
  }
}