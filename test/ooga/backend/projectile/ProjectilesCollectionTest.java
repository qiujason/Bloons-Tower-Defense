package ooga.backend.projectile;

import static org.junit.jupiter.api.Assertions.*;

import ooga.backend.projectile.types.SingleTargetProjectile;
import org.junit.jupiter.api.Test;

class ProjectilesCollectionTest {

  ProjectilesCollection collection = new ProjectilesCollection();

  @Test
  void testAdd() {
    Projectile dart = new SingleTargetProjectile(ProjectileType.SingleTargetProjectile, 10,15,-5,5,0);
    collection.add(dart);
    assertEquals(1, collection.size());
  }

  @Test
  void testRemove() {
    Projectile dart = new SingleTargetProjectile(ProjectileType.SingleTargetProjectile, 10,15,-5,5,0);
    collection.add(dart);
    assertEquals(1, collection.size());
    collection.remove(dart);
    assertEquals(0, collection.size());
  }

  @Test
  void testClear() {
    Projectile dart = new SingleTargetProjectile(ProjectileType.SingleTargetProjectile, 10,15,-5,5,0);
    collection.add(dart);
    collection.add(dart);
    collection.clear();
    assertEquals(0, collection.size());
  }

  @Test
  void testCreateIterator() {
    Projectile dart = new SingleTargetProjectile(ProjectileType.SingleTargetProjectile, 10,15,-5,5,0);
    collection.add(dart);
    assertTrue(collection.createIterator().hasNext());
  }

  @Test
  void testSize() {
    Projectile dart = new SingleTargetProjectile(ProjectileType.SingleTargetProjectile, 10,15,-5,5,0);
    collection.add(dart);
    collection.add(dart);
    assertEquals(2, collection.size());
  }

  @Test
  void testContains() {
    Projectile dart = new SingleTargetProjectile(ProjectileType.SingleTargetProjectile, 10,15,-5,5,0);
    collection.add(dart);
    assertTrue(collection.contains(dart));
  }

  @Test
  void testIsEmpty() {
    assertTrue(collection.isEmpty());
    Projectile dart = new SingleTargetProjectile(ProjectileType.SingleTargetProjectile, 10,15,-5,5,0);
    collection.add(dart);
    assertFalse(collection.isEmpty());
  }
}