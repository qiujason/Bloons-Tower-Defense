package ooga.backend.readers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Map;
import ooga.backend.roaditems.RoadItemType;
import org.junit.jupiter.api.Test;

class RoadItemValueReaderTest {

  @Test
  void getMap() throws IOException {
    RoadItemValueReader roadItemValueReader = new RoadItemValueReader("towervalues/roadItemBuyValues.properties");
    Map<RoadItemType, Integer> itemValueReaderMap = roadItemValueReader.getMap();
    assertEquals(30, itemValueReaderMap.get(RoadItemType.PopBloonsItem));
    assertEquals(15, itemValueReaderMap.get(RoadItemType.SlowBloonsItem));
    assertEquals(30, itemValueReaderMap.get(RoadItemType.ExplodeBloonsItem));
  }

  @Test
  void getRoadItemType() throws IOException {
    RoadItemValueReader roadItemValueReader = new RoadItemValueReader("towervalues/roadItemBuyValues.properties");
    assertEquals(RoadItemType.PopBloonsItem, roadItemValueReader.getRoadItemType("PopBloonsItem"));
    assertEquals(RoadItemType.SlowBloonsItem, roadItemValueReader.getRoadItemType("SlowBloonsItem"));
    assertEquals(RoadItemType.ExplodeBloonsItem, roadItemValueReader.getRoadItemType("ExplodeBloonsItem"));

  }
}