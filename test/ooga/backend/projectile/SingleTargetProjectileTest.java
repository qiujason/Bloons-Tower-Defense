package ooga.backend.projectile;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SingleTargetProjectileTest {

  @Test
  void updatePosition() {
    Projectile dart = new SingleTargetProjectile(ProjectileType.SingleTargetProjectile, 10,15,-5,5);
    dart.updatePosition();
    assertEquals(5, dart.getXPosition());
    assertEquals(20, dart.getYPosition());
  }

  @Test
  void setXVelocity() {
    Projectile dart = new SingleTargetProjectile(ProjectileType.SingleTargetProjectile, 10,15,-5,5);
    dart.setXVelocity(0);
    assertEquals(0, dart.getXVelocity());
  }

  @Test
  void setYVelocity() {
    Projectile dart = new SingleTargetProjectile(ProjectileType.SingleTargetProjectile, 10,15,-5,5);
    dart.setYVelocity(0);
    assertEquals(0, dart.getYVelocity());
  }

  @Test
  void getXPosition() {
    Projectile dart = new SingleTargetProjectile(ProjectileType.SingleTargetProjectile, 10,15,-5,5);
    assertEquals(10, dart.getXPosition());
  }

  @Test
  void getYPosition() {
    Projectile dart = new SingleTargetProjectile(ProjectileType.SingleTargetProjectile, 10,15,-5,5);
    assertEquals(15, dart.getYPosition());
  }

  @Test
  void getXVelocity() {
    Projectile dart = new SingleTargetProjectile(ProjectileType.SingleTargetProjectile, 10,10,-5,5);
    assertEquals(-5, dart.getXVelocity());
  }

  @Test
  void getYVelocity() {
    Projectile dart = new SingleTargetProjectile(ProjectileType.SingleTargetProjectile, 10,10,-5,5);
    assertEquals(5, dart.getYVelocity());
  }
}