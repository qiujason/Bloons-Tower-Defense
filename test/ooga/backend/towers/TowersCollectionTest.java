package ooga.backend.towers;

import static org.junit.jupiter.api.Assertions.*;

import ooga.backend.towers.singleshottowers.SingleProjectileShooter;
import org.junit.jupiter.api.Test;

class TowersCollectionTest {

  TowersCollection collection = new TowersCollection();

  @Test
  void testAdd() {
    Tower testTower = new SingleProjectileShooter(0,0,5,10,5);
    collection.add(testTower);
    assertEquals(1, collection.size());
  }

  @Test
  void testRemove() {
    Tower testTower = new SingleProjectileShooter(0,0,5,10,5);
    collection.add(testTower);
    assertEquals(1, collection.size());
    collection.remove(testTower);
    assertEquals(0, collection.size());
  }

  @Test
  void testClear() {
    Tower testTower = new SingleProjectileShooter(0,0,5,10,5);
    collection.add(testTower);
    collection.add(testTower);
    collection.clear();
    assertEquals(0, collection.size());
  }

  @Test
  void testCreateIterator() {
    Tower testTower = new SingleProjectileShooter(0,0,5,10,5);
    collection.add(testTower);
    assertTrue(collection.createIterator().hasNext());
  }

  @Test
  void testSize() {
    Tower testTower = new SingleProjectileShooter(0,0,5,10,5);
    collection.add(testTower);
    collection.add(testTower);
    assertEquals(2, collection.size());
  }

  @Test
  void testContains() {
    Tower testTower = new SingleProjectileShooter(0,0,5,10,5);
    collection.add(testTower);
    collection.updateAll();
    assertTrue(collection.contains(testTower));
  }

  @Test
  void testIsEmpty() {
    assertTrue(collection.isEmpty());
    Tower testTower = new SingleProjectileShooter(0,0,5,10,5);
    collection.add(testTower);
    assertFalse(collection.isEmpty());
  }
}