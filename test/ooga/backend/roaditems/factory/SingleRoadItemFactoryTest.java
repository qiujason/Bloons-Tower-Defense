package ooga.backend.roaditems.factory;

import static org.junit.jupiter.api.Assertions.*;

import ooga.backend.roaditems.RoadItem;
import ooga.backend.roaditems.RoadItemType;
import org.junit.jupiter.api.Test;

class SingleRoadItemFactoryTest {

  @Test
  void createRoadItem() {
    RoadItem testRoadItem = new SingleRoadItemFactory().createRoadItem(RoadItemType.PopBloonsItem, 0,0);
    assertEquals(RoadItemType.PopBloonsItem, testRoadItem.getType());
  }
}