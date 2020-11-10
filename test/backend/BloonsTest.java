package backend;

import static org.junit.jupiter.api.Assertions.*;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.collection.BloonsCollection;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.collections.Iterator;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.projectile.SingleTargetProjectile;
import ooga.backend.bloons.factory.BasicBloonsFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class BloonsTest {

  BloonsTypeChain chain;

  @BeforeAll
  void initializeBloonsTypes() {
    chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
  }

  @Test
  void testCreateBloonsFromFactory() {
    assertNotNull((new BasicBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0)));
  }

  @Test
  void testBloonsPositionUpdate() {
    Bloon bloon = new Bloon(chain.getBloonsTypeRecord("RED"), 10, 10, 10, 10);
    bloon.update();
    assertEquals(20, bloon.getXPosition());
    assertEquals(20, bloon.getYPosition());
  }

  @Test
  void testBloonsDistanceTraveled() {
    Bloon bloon = new Bloon(chain.getBloonsTypeRecord("RED"), 10, 10, 10, 10);
    bloon.update();
    assertEquals(20, bloon.getDistanceTraveled());
  }

  @Test
  void testBloonsChangedVelocityPositionUpdate() {
    Bloon bloon = new Bloon(chain.getBloonsTypeRecord("RED"), 10, 10, 10, 10);
    bloon.update();
    assertEquals(20, bloon.getXPosition());
    assertEquals(20, bloon.getYPosition());
    bloon.setXVelocity(-5);
    bloon.update();
    assertEquals(15, bloon.getXPosition());
    assertEquals(30, bloon.getYPosition());
    bloon.setYVelocity(20);
    bloon.update();
    assertEquals(10, bloon.getXPosition());
    assertEquals(50, bloon.getYPosition());
  }

  @Test
  void testAddBloonsCollection() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloon newBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertTrue(bloonsIterator.hasMore());
  }

  @Test
  void testAddNonBloonCollection() {
    BloonsCollection list = new BloonsCollection();
    assertFalse(list.add(new SingleTargetProjectile(ProjectileType.SingleTargetProjectile, 0, 0, 0, 0)));
  }

  @Test
  void testRemoveBloonsCollection() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloon newBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertEquals(newBloon, bloonsIterator.getNext());
    assertTrue(list.remove(newBloon));
    assertFalse(bloonsIterator.hasMore());
  }

  @Test
  void testRemoveBloonsNotInCollection() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloon newBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertEquals(newBloon, bloonsIterator.getNext());
    assertFalse(list.remove(new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0)));
  }

  @Test
  void testRemoveNonBloonCollection() {
    BloonsCollection list = new BloonsCollection();
    assertFalse(list.remove(new SingleTargetProjectile(ProjectileType.SingleTargetProjectile, 0, 0, 0, 0)));
  }

  @Test
  void testRemoveEmptyCollection() {
    BloonsCollection list = new BloonsCollection();
    assertFalse(list.remove(new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0)));
  }

  @Test
  void testHasMoreIterator() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloon newBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertTrue(bloonsIterator.hasMore());
  }

  @Test
  void testMaxOfIterator() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloon newBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertTrue(bloonsIterator.hasMore());
    bloonsIterator.getNext();
    assertFalse(bloonsIterator.hasMore());
  }

  @Test
  void testResetIterator() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloon newBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
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
    Bloon bloon1 = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 0);
    assertTrue(list.add(bloon1));
    assertEquals(bloon1, bloonsIterator.getNext());
    Bloon bloon2 = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 16, 0);
    assertTrue(list.add(bloon2));
    assertEquals(bloon2, bloonsIterator.getNext());
  }

  @Test
  void testGetNextResetBloonsIterator() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloon bloon1 = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 0);
    assertTrue(list.add(bloon1));
    assertEquals(bloon1, bloonsIterator.getNext());
    Bloon bloon2 = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 16, 0);
    assertTrue(list.add(bloon2));
    assertEquals(bloon2, bloonsIterator.getNext());
    bloonsIterator.reset();
    assertEquals(bloon1, bloonsIterator.getNext());
  }

  @Test
  void testAddSortBloonsIterator() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloon slowBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 0);
    assertTrue(list.add(slowBloon));
    slowBloon.update();
    assertEquals(slowBloon, bloonsIterator.getNext());
    bloonsIterator.reset();
    Bloon fastBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 16, 0);
    fastBloon.update();
    list.add(fastBloon);
    assertEquals(fastBloon, bloonsIterator.getNext());
  }

  @Test
  void testSortCollection() {
    BloonsCollection list = new BloonsCollection();
    Iterator bloonsIterator = list.createIterator();
    Bloon slowBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 0);
    assertTrue(list.add(slowBloon));
    slowBloon.update();

    Bloon fastBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 16, 0);
    assertTrue(list.add(fastBloon));
    list.updateAll();
    assertEquals(slowBloon, bloonsIterator.getNext());
    bloonsIterator.reset();
    list.updateAll();
    assertEquals(fastBloon, bloonsIterator.getNext());
  }

}
