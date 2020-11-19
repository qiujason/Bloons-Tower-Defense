package ooga.backend.roaditems;

import static org.junit.jupiter.api.Assertions.*;

import ooga.backend.roaditems.factory.SingleRoadItemFactory;
import org.junit.jupiter.api.Test;

class RoadItemsCollectionTest {
  RoadItemsCollection collection = new RoadItemsCollection();

  @Test
  void testAdd() {
    RoadItem testRoadItem = new SingleRoadItemFactory().createRoadItem(RoadItemType.PopBloonsItem, 0,0);
    collection.add(testRoadItem);
    assertEquals(1, collection.size());
  }

  @Test
  void testRemove() {
    RoadItem testRoadItem = new SingleRoadItemFactory().createRoadItem(RoadItemType.PopBloonsItem, 0,0);
    collection.add(testRoadItem);
    collection.updateAll();
    assertEquals(1, collection.size());
    collection.remove(testRoadItem);
    assertEquals(0, collection.size());
  }

  @Test
  void testClear() {
    RoadItem testRoadItem = new SingleRoadItemFactory().createRoadItem(RoadItemType.PopBloonsItem, 0,0);
    collection.add(testRoadItem);
    collection.add(testRoadItem);
    collection.clear();
    assertEquals(0, collection.size());
  }

  @Test
  void testCreateIterator() {
    RoadItem testRoadItem = new SingleRoadItemFactory().createRoadItem(RoadItemType.PopBloonsItem, 0,0);
    collection.add(testRoadItem);
    assertTrue(collection.createIterator().hasNext());
  }

  @Test
  void testSize() {
    RoadItem testRoadItem = new SingleRoadItemFactory().createRoadItem(RoadItemType.PopBloonsItem, 0,0);
    collection.add(testRoadItem);
    collection.add(testRoadItem);
    assertEquals(2, collection.size());
  }

  @Test
  void testContains() {
    RoadItem testRoadItem = new SingleRoadItemFactory().createRoadItem(RoadItemType.PopBloonsItem, 0,0);
    collection.add(testRoadItem);
    assertTrue(collection.contains(testRoadItem));
  }

  @Test
  void testIsEmpty() {
    assertTrue(collection.isEmpty());
    RoadItem testRoadItem = new SingleRoadItemFactory().createRoadItem(RoadItemType.PopBloonsItem, 0,0);
    collection.add(testRoadItem);
    assertFalse(collection.isEmpty());
  }
}