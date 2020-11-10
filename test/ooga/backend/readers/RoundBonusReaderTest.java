package ooga.backend.readers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class RoundBonusReaderTest {

  @Test
  void testGetDataFromFile() {
    RoundBonusReader roundBonusReader = new RoundBonusReader();
    List<List<String>> bonus = roundBonusReader.getDataFromFile("roundBonuses/BTD5_default_level1_to_10.csv");
    assertEquals("10", bonus.get(0).get(0));
    assertEquals("100", bonus.get(1).get(0));
    assertEquals("101", bonus.get(1).get(1));
    assertEquals("102", bonus.get(1).get(2));
    assertEquals("103", bonus.get(1).get(3));
    assertEquals("104", bonus.get(1).get(4));
    assertEquals("105", bonus.get(1).get(5));
    assertEquals("106", bonus.get(1).get(6));
    assertEquals("107", bonus.get(1).get(7));
    assertEquals("108", bonus.get(1).get(8));
    assertEquals("109", bonus.get(1).get(9));
  }
}