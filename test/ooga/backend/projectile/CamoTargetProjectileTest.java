package ooga.backend.projectile;

import static org.junit.jupiter.api.Assertions.*;

import ooga.backend.ConfigurationException;
import ooga.backend.projectile.factory.SingleProjectileFactory;
import ooga.backend.projectile.types.CamoTargetProjectile;
import org.junit.jupiter.api.Test;

class CamoTargetProjectileTest {

  CamoTargetProjectile projectile;

  @Test
  void getType() throws ConfigurationException {
    projectile = (CamoTargetProjectile) new SingleProjectileFactory().createDart(ProjectileType.CamoTargetProjectile, 0, 0, 1, 1, 30);
    assertEquals(ProjectileType.CamoTargetProjectile, projectile.getType());
  }

  @Test
  void getRadius() throws ConfigurationException {
    projectile = (CamoTargetProjectile) new SingleProjectileFactory().createDart(ProjectileType.CamoTargetProjectile, 0, 0, 1, 1, 30);
    assertEquals(1, projectile.getRadius());
  }

  @Test
  void testUpdateAndUpdatePosition() throws ConfigurationException {
    projectile = (CamoTargetProjectile) new SingleProjectileFactory().createDart(ProjectileType.CamoTargetProjectile, 0, 0, 1, 1, 30);
    projectile.update();
    assertEquals(1, projectile.getXPosition());
    assertEquals(1, projectile.getYPosition());
  }
}