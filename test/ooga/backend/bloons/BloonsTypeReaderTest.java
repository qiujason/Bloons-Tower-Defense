package ooga.backend.bloons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;
import org.junit.jupiter.api.Test;

public class BloonsTypeReaderTest {

  @Test
  void testGetBloonsTypeRecordByName() {
    BloonsTypeChain chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
    assertEquals(new BloonsType("RED",1, 1, new HashSet<>()), chain.getBloonsTypeRecord("RED"));
  }

  @Test
  void testGetNextBloonsTypeRecord() {
    BloonsTypeChain chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
    assertEquals(new BloonsType("DEAD",0, 0, new HashSet<>()), chain.getNextBloonsType(chain.getBloonsTypeRecord("RED")));
  }

  @Test
  void testGetPrevBloonsTypeRecordBeginning() {
    BloonsTypeChain chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
    assertNull(chain.getNextBloonsType(chain.getBloonsTypeRecord("DEAD")));
  }

  @Test
  void testGetPrevBloonsTypeRecord() {
    BloonsTypeChain chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
    assertEquals(new BloonsType("RAINBOW",47, 2.2, new HashSet<>()), chain.getPrevBloonsType(chain.getBloonsTypeRecord("ZEBRA")));
  }

  @Test
  void testGetPrevBloonsTypeRecordEnd() {
    BloonsTypeChain chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
    assertNull(chain.getPrevBloonsType(chain.getBloonsTypeRecord("RAINBOW")));
  }

  @Test
  void testGetBloonsTypeRecordByIndex() {
    BloonsTypeChain chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
    assertEquals(new BloonsType("DEAD",0, 0, new HashSet<>()), chain.getBloonsTypeRecord(0));
    assertEquals(new BloonsType("RED",1, 1, new HashSet<>()), chain.getBloonsTypeRecord(1));
  }

  @Test
  void testGetBloonsTypeRecordByIndexOutOfBloons() {
    BloonsTypeChain chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
    assertNull(chain.getBloonsTypeRecord(100));
  }

  @Test
  void testGetBloonsTypeRecordByNegativeIndex() {
    BloonsTypeChain chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
    assertEquals(new BloonsType("DEAD",0, 0, new HashSet<>()), chain.getBloonsTypeRecord(-1));
  }

  @Test
  void testGetNumNextBloons1() {
    BloonsTypeChain chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
    assertEquals(1, chain.getNumNextBloons(chain.getBloonsTypeRecord("GREEN")));
  }

  @Test
  void testGetNumNextBloons2() {
    BloonsTypeChain chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
    assertEquals(2, chain.getNumNextBloons(chain.getBloonsTypeRecord("RAINBOW")));
  }

}
