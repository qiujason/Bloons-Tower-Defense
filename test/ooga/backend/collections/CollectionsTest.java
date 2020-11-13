package ooga.backend.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    Bloon newBloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    assertTrue(bloonsIterator.hasNext());
  }

  @Test
  void testRemoveBloonsCollection() {
    BloonsCollection list = new BloonsCollection();
    Bloon newBloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    assertEquals(newBloon, bloonsIterator.next());
    bloonsIterator.remove(newBloon);
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
  void testHasMoreIterator() {
    BloonsCollection list = new BloonsCollection();
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    Bloon newBloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(list.add(newBloon));
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
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    Bloon bloon1 = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 10, 0);
    assertTrue(list.add(bloon1));
    assertEquals(bloon1, bloonsIterator.next());
    Bloon bloon2 = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 16, 0);
    assertTrue(list.add(bloon2));
    assertEquals(bloon2, bloonsIterator.next());
  }

  @Test
  void testGetNextResetBloonsIterator() {
    BloonsCollection list = new BloonsCollection();
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    Bloon bloon1 = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 10, 0);
    assertTrue(list.add(bloon1));
    assertEquals(bloon1, bloonsIterator.next());
    Bloon bloon2 = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 16, 0);
    assertTrue(list.add(bloon2));
    assertEquals(bloon2, bloonsIterator.next());
    bloonsIterator.reset();
    assertEquals(bloon1, bloonsIterator.next());
  }

  @Test
  void testAddSortBloonsIterator() {
    BloonsCollection list = new BloonsCollection();
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    Bloon slowBloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 10, 0);
    assertTrue(list.add(slowBloon));
    slowBloon.update();
    assertEquals(slowBloon, bloonsIterator.next());
    bloonsIterator.reset();
    Bloon fastBloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 16, 0);
    fastBloon.update();
    list.add(fastBloon);
    assertEquals(fastBloon, bloonsIterator.next());
  }

  @Test
  void testSortCollection() {
    BloonsCollection list = new BloonsCollection();
    GamePieceIterator<Bloon> bloonsIterator = list.createIterator();
    Bloon slowBloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 10, 0);
    assertTrue(list.add(slowBloon));
    slowBloon.update();

    Bloon fastBloon = new Bloon(chain, chain.getBloonsTypeRecord("RED"), 0, 0, 16, 0);
    assertTrue(list.add(fastBloon));
    list.updateAll();
    assertEquals(slowBloon, bloonsIterator.next());
    bloonsIterator.reset();
    list.updateAll();
    assertEquals(fastBloon, bloonsIterator.next());
  }

}
