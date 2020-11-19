package ooga.backend.roaditems.types;

import static org.junit.jupiter.api.Assertions.*;

import ooga.backend.roaditems.RoadItemType;
import org.junit.jupiter.api.Test;

class ExplodeBloonsItemTest {

  ExplodeBloonsItem item = new ExplodeBloonsItem(10,10);

  @Test
  void testShouldRemoveAndUpdate() {
    item.update();
    assertFalse(item.shouldRemove());
    for(int i = 1; i < 60 * 3; i++){
      item.update();
    }
    assertTrue(item.shouldRemove());
  }

  @Test
  void testGetType() {
    assertEquals(RoadItemType.ExplodeBloonsItem, item.getType());
  }

}