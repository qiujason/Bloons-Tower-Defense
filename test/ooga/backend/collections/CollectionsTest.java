package ooga.backend.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.types.BloonsTypeChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CollectionsTest {

  private BloonsTypeChain chain;

  @BeforeEach
  void initializeBloonsTypes() {
    chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
  }

  @Test
  void testAddBloonsCollection() {
    GamePieceCollection<Bloon> list = new BloonsCollection();
    Bloon newBloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    assertTrue(bloonsIterator.hasNext());
  }

  @Test
  void testRemoveBloonsConcurrentlyCollection() {
    BloonsCollection list = new BloonsCollection();
    Bloon newBloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    assertEquals(newBloon, bloonsIterator.next());
    list.remove(newBloon);
    bloonsIterator.reset();
    assertTrue(bloonsIterator.hasNext());
  }

  @Test
  void testRemoveBloonsCollection() {
    BloonsCollection list = new BloonsCollection();
    Bloon newBloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    assertEquals(newBloon, bloonsIterator.next());
    list.remove(newBloon);
    bloonsIterator = list.createIterator();
    assertFalse(bloonsIterator.hasNext());
  }

  @Test
  void testRemoveBloonsNotInCollection() {
    BloonsCollection list = new BloonsCollection();
    Bloon newBloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertFalse(list.remove(new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0)));
  }

  @Test
  void testRemoveEmptyCollection() {
    BloonsCollection list = new BloonsCollection();
    assertFalse(list.remove(new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0)));
  }

  @Test
  void testHasNextIterator() {
    BloonsCollection list = new BloonsCollection();
    Bloon newBloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    assertTrue(bloonsIterator.hasNext());
  }

  @Test
  void testMaxOfIterator() {
    BloonsCollection list = new BloonsCollection();
    Bloon newBloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    assertTrue(bloonsIterator.hasNext());
    bloonsIterator.next();
    assertFalse(bloonsIterator.hasNext());
  }

  @Test
  void testResetIterator() {
    BloonsCollection list = new BloonsCollection();
    Bloon newBloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    assertTrue(bloonsIterator.hasNext());
    bloonsIterator.next();
    assertFalse(bloonsIterator.hasNext());
    bloonsIterator.reset();
    assertTrue(bloonsIterator.hasNext());
  }

  @Test
  void testGetNextBloonsIterator() {
    BloonsCollection list = new BloonsCollection();
    Bloon bloon1 = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 10, 0);
    assertTrue(list.add(bloon1));
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    assertEquals(bloon1, bloonsIterator.next());
  }

  @Test
  void testGetNextResetBloonsIterator() {
    BloonsCollection list = new BloonsCollection();
    Bloon bloon1 = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 10, 0);
    assertTrue(list.add(bloon1));
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    assertEquals(bloon1, bloonsIterator.next());
    assertNull(bloonsIterator.next());
    bloonsIterator.reset();
    assertEquals(bloon1, bloonsIterator.next());
  }

  @Test
  void testAddSortBloonsIterator() {
    BloonsCollection list = new BloonsCollection();
    Bloon slowBloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 10, 0);
    assertTrue(list.add(slowBloon));
    slowBloon.update();
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    assertEquals(slowBloon, bloonsIterator.next());
    Bloon fastBloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 16, 0);
    fastBloon.update();
    list.add(fastBloon);
    bloonsIterator = list.createIterator();
    assertEquals(fastBloon, bloonsIterator.next());
  }

  @Test
  void testSortCollection() {
    BloonsCollection list = new BloonsCollection();
    Bloon slowBloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 10, 0);
    assertTrue(list.add(slowBloon));
    slowBloon.update();

    Bloon fastBloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 16, 0);
    assertTrue(list.add(fastBloon));
    list.updateAll();
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    assertEquals(slowBloon, bloonsIterator.next());
    list.updateAll();
    bloonsIterator = list.createIterator();
    assertEquals(fastBloon, bloonsIterator.next());
  }

  @Test
  void testConcurrentModificationAdd() {
    BloonsCollection list = new BloonsCollection();
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    Bloon bloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 10, 0);
    list.add(bloon);
    assertNull(bloonsIterator.next());
  }

  @Test
  void testConcurrentModificationRemove() {
    BloonsCollection list = new BloonsCollection();
    Bloon bloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 10, 0);
    list.add(bloon);
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    list.remove(bloon);
    assertEquals(bloon, bloonsIterator.next());
  }

}
