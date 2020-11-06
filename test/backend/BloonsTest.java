package backend;

import static org.junit.jupiter.api.Assertions.*;

import ooga.backend.bloons.Bloons;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.collections.Iterator;
import ooga.backend.darts.SingleDart;
import ooga.backend.factory.BasicBloonsFactory;
import org.junit.jupiter.api.Test;


public class BloonsTest {

  @Test
  void testCreateBloonsFromFactory() {
    assertNotNull((new BasicBloonsFactory().createBloons(0, 0, 0, 0, 0)));
  }

  @Test
  void testDecrementLife() {
    Bloons bloon = new BasicBloonsFactory().createBloons(1, 0, 0, 0, 0);
    bloon.decrementLives(1);
    assertEquals(0, bloon.getLives());
  }

  @Test
  void testBloonsPositionUpdate() {
    Bloons bloon = new BasicBloonsFactory().createBloons(0, 10, 10, 10, 10);
    bloon.updatePosition();
    assertEquals(20, bloon.getXPosition());
    assertEquals(20, bloon.getYPosition());
  }

  @Test
  void testBloonsDistanceTraveled() {
    Bloons bloon = new BasicBloonsFactory().createBloons(0, 10, 10, 10, 10);
    bloon.updatePosition();
    assertEquals(20, bloon.getDistanceTraveled());
  }

  @Test
  void testBloonsChangedVelocityPositionUpdate() {
    Bloons bloon = new BasicBloonsFactory().createBloons(0, 10, 10, 10, 10);
    bloon.updatePosition();
    assertEquals(20, bloon.getXPosition());
    assertEquals(20, bloon.getYPosition());
    bloon.setXVelocity(-5);
    bloon.updatePosition();
    assertEquals(15, bloon.getXPosition());
    assertEquals(30, bloon.getYPosition());
    bloon.setYVelocity(20);
    bloon.updatePosition();
    assertEquals(10, bloon.getXPosition());
    assertEquals(50, bloon.getYPosition());
  }

  @Test
  void testAddBloonsCollection() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloons newBloon = new Bloons(0, 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertTrue(bloonsIterator.hasMore());
  }

  @Test
  void testAddNonBloonCollection() {
    BloonsCollection list = new BloonsCollection();
    assertFalse(list.add(new SingleDart(0, 0, 0, 0)));
  }

  @Test
  void testRemoveBloonsCollection() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloons newBloon = new Bloons(0, 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertEquals(newBloon, bloonsIterator.getNext());
    assertTrue(list.remove(newBloon));
    assertFalse(bloonsIterator.hasMore());
  }

  @Test
  void testRemoveBloonsNotInCollection() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloons newBloon = new Bloons(0, 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertEquals(newBloon, bloonsIterator.getNext());
    assertFalse(list.remove(new Bloons(0, 0, 0, 0, 0)));
  }

  @Test
  void testRemoveNonBloonCollection() {
    BloonsCollection list = new BloonsCollection();
    assertFalse(list.remove(new SingleDart(0, 0, 0, 0)));
  }

  @Test
  void testRemoveEmptyCollection() {
    BloonsCollection list = new BloonsCollection();
    assertFalse(list.remove(new Bloons(0, 0, 0, 0, 0)));
  }

  @Test
  void testHasMoreIterator() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloons newBloon = new Bloons(0, 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertTrue(bloonsIterator.hasMore());
  }

  @Test
  void testMaxOfIterator() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloons newBloon = new Bloons(0, 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertTrue(bloonsIterator.hasMore());
    bloonsIterator.getNext();
    assertFalse(bloonsIterator.hasMore());
  }

  @Test
  void testResetIterator() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloons newBloon = new Bloons(0, 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertTrue(bloonsIterator.hasMore());
    bloonsIterator.getNext();
    assertFalse(bloonsIterator.hasMore());
    bloonsIterator.reset();
    assertTrue(bloonsIterator.hasMore());
  }

  @Test
  void testGetNextBloonsIterator() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloons bloon1 = new Bloons(0, 0, 0, 10, 0);
    assertTrue(list.add(bloon1));
    assertEquals(bloon1, bloonsIterator.getNext());
    Bloons bloon2 = new Bloons(0, 0, 0, 16, 0);
    assertTrue(list.add(bloon2));
    assertEquals(bloon2, bloonsIterator.getNext());
  }

  @Test
  void testGetNextResetBloonsIterator() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloons bloon1 = new Bloons(0, 0, 0, 10, 0);
    assertTrue(list.add(bloon1));
    assertEquals(bloon1, bloonsIterator.getNext());
    Bloons bloon2 = new Bloons(0, 0, 0, 16, 0);
    assertTrue(list.add(bloon2));
    assertEquals(bloon2, bloonsIterator.getNext());
    bloonsIterator.reset();
    assertEquals(bloon1, bloonsIterator.getNext());
  }

  @Test
  void testAddSortBloonsIterator() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloons slowBloon = new Bloons(0, 0, 0, 10, 0);
    assertTrue(list.add(slowBloon));
    slowBloon.updatePosition();
    assertEquals(slowBloon, bloonsIterator.getNext());
    bloonsIterator.reset();
    Bloons fastBloon = new Bloons(0, 0, 0, 16, 0);
    fastBloon.updatePosition();
    list.add(fastBloon);
    assertEquals(fastBloon, bloonsIterator.getNext());
  }

  @Test
  void testSortCollection() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloons slowBloon = new Bloons(0, 0, 0, 10, 0);
    assertTrue(list.add(slowBloon));
    slowBloon.updatePosition();

    Bloons fastBloon = new Bloons(0, 0, 0, 16, 0);
    assertTrue(list.add(fastBloon));
    for (int i = 0; i < 2; i++) {
      slowBloon.updatePosition();
      fastBloon.updatePosition();
      assertEquals(slowBloon, bloonsIterator.getNext());
      list.sort();
      bloonsIterator.reset();
    }
    assertEquals(fastBloon, bloonsIterator.getNext());
  }
}
