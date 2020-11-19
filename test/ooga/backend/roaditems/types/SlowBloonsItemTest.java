package ooga.backend.roaditems.types;

import static org.junit.jupiter.api.Assertions.*;

import ooga.backend.roaditems.RoadItemType;
import org.junit.jupiter.api.Test;

class SlowBloonsItemTest {

  SlowBloonsItem item = new SlowBloonsItem(10,10);

  @Test
  void testShouldRemoveAndUpdate() {
    item.update();
    assertFalse(item.shouldRemove());
    for(int i = 1; i < 10; i++){
      item.update();
    }
    assertTrue(item.shouldRemove());
  }

  @Test
  void testGetType() {
    assertEquals(RoadItemType.SlowBloonsItem, item.getType());
  }
}