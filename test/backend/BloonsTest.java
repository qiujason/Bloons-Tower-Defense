package backend;

import static org.junit.jupiter.api.Assertions.*;

import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.collections.GamePieceCollection;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.bloons.factory.BasicBloonsFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BloonsTest {

  BloonsTypeChain chain;

  @BeforeEach
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
    GamePieceCollection<Bloon> list = new BloonsCollection();
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    Bloon newBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertTrue(bloonsIterator.hasNext());
  }

  @Test
  void testRemoveBloonsCollection() {
    BloonsCollection list = new BloonsCollection();
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    Bloon newBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertEquals(newBloon, bloonsIterator.next());
    assertTrue(list.remove(newBloon));
    assertFalse(bloonsIterator.hasNext());
  }

  @Test
  void testRemoveBloonsNotInCollection() {
    BloonsCollection list = new BloonsCollection();
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    Bloon newBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertEquals(newBloon, bloonsIterator.next());
    assertFalse(list.remove(new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0)));
  }

  @Test
  void testRemoveEmptyCollection() {
    BloonsCollection list = new BloonsCollection();
    assertFalse(list.remove(new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0)));
  }

  @Test
  void testHasMoreIterator() {
    BloonsCollection list = new BloonsCollection();
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    Bloon newBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertTrue(bloonsIterator.hasNext());
  }

  @Test
  void testMaxOfIterator() {
    BloonsCollection list = new BloonsCollection();
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    Bloon newBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertTrue(bloonsIterator.hasNext());
    bloonsIterator.next();
    assertFalse(bloonsIterator.hasNext());
  }

  @Test
  void testResetIterator() {
    BloonsCollection list = new BloonsCollection();
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    Bloon newBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertTrue(bloonsIterator.hasNext());
    bloonsIterator.next();
    assertFalse(bloonsIterator.hasNext());
    bloonsIterator.reset();
    assertTrue(bloonsIterator.hasNext());
  }

  @Test
  void testGetNextBloonsIterator() {
    BloonsCollection list = new BloonsCollection();
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    Bloon bloon1 = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 0);
    assertTrue(list.add(bloon1));
    assertEquals(bloon1, bloonsIterator.next());
    Bloon bloon2 = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 16, 0);
    assertTrue(list.add(bloon2));
    assertEquals(bloon2, bloonsIterator.next());
  }

  @Test
  void testGetNextResetBloonsIterator() {
    BloonsCollection list = new BloonsCollection();
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    Bloon bloon1 = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 0);
    assertTrue(list.add(bloon1));
    assertEquals(bloon1, bloonsIterator.next());
    Bloon bloon2 = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 16, 0);
    assertTrue(list.add(bloon2));
    assertEquals(bloon2, bloonsIterator.next());
    bloonsIterator.reset();
    assertEquals(bloon1, bloonsIterator.next());
  }

  @Test
  void testAddSortBloonsIterator() {
    BloonsCollection list = new BloonsCollection();
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    Bloon slowBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 0);
    assertTrue(list.add(slowBloon));
    slowBloon.update();
    assertEquals(slowBloon, bloonsIterator.next());
    bloonsIterator.reset();
    Bloon fastBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 16, 0);
    fastBloon.update();
    list.add(fastBloon);
    assertEquals(fastBloon, bloonsIterator.next());
  }

  @Test
  void testSortCollection() {
    BloonsCollection list = new BloonsCollection();
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    Bloon slowBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 0);
    assertTrue(list.add(slowBloon));
    slowBloon.update();

    Bloon fastBloon = new Bloon(chain.getBloonsTypeRecord("RED"), 0, 0, 16, 0);
    assertTrue(list.add(fastBloon));
    list.updateAll();
    assertEquals(slowBloon, bloonsIterator.next());
    bloonsIterator.reset();
    list.updateAll();
    assertEquals(fastBloon, bloonsIterator.next());
  }

}
