package ooga.backend.roaditems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RoadItemTypeTest {

  @Test
  void isEnumName() {
    assertTrue(RoadItemType.isEnumName("PopBloonsItem"));
    assertTrue(RoadItemType.isEnumName("ExplodeBloonsItem"));
    assertTrue(RoadItemType.isEnumName("SlowBloonsItem"));
    assertFalse(RoadItemType.isEnumName("FakeName"));
  }

  @Test
  void fromString() {
    assertEquals(RoadItemType.SlowBloonsItem, RoadItemType.fromString("SlowBloonsItem"));
    assertEquals(RoadItemType.ExplodeBloonsItem, RoadItemType.fromString("ExplodeBloonsItem"));
    assertEquals(RoadItemType.PopBloonsItem, RoadItemType.fromString("PopBloonsItem"));
  }
}