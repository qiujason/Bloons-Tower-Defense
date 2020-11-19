package ooga.backend.bloons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;
import ooga.backend.ConfigurationException;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.bloons.types.Specials;
import org.junit.jupiter.api.Test;

public class BloonsTypeChainTest {

  @Test
  void testGetBloonsTypeRecordByName() throws ConfigurationException {
    BloonsTypeChain chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
    assertEquals(new BloonsType(chain, "RED",1, 1, Specials.None), chain.getBloonsTypeRecord("RED"));
  }

  @Test
  void testGetNextBloonsTypeRecord() throws ConfigurationException {
    BloonsTypeChain chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
    assertEquals(new BloonsType(chain, "DEAD",0, 0, Specials.None), chain.getNextBloonsType(chain.getBloonsTypeRecord("RED")));
  }

  @Test
  void testGetPrevBloonsTypeRecordBeginning() throws ConfigurationException {
    BloonsTypeChain chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
    assertEquals(chain.getBloonsTypeRecord("DEAD"), chain.getNextBloonsType(chain.getBloonsTypeRecord("DEAD")));
  }

  @Test
  void testGetPrevBloonsTypeRecord() throws ConfigurationException {
    BloonsTypeChain chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
    assertEquals(new BloonsType(chain, "RAINBOW",47, 2.2, Specials.None), chain.getPrevBloonsType(chain.getBloonsTypeRecord("ZEBRA")));
  }

  @Test
  void testGetPrevBloonsTypeRecordEnd() throws ConfigurationException {
    BloonsTypeChain chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
    assertNull(chain.getPrevBloonsType(chain.getBloonsTypeRecord("RAINBOW")));
  }

  @Test
  void testGetBloonsTypeRecordByIndex() throws ConfigurationException {
    BloonsTypeChain chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
    assertEquals(new BloonsType(chain, "DEAD",0, 0, Specials.None), chain.getBloonsTypeRecord(0));
    assertEquals(new BloonsType(chain, "RED",1, 1, Specials.None), chain.getBloonsTypeRecord(1));
  }

  @Test
  void testGetBloonsTypeRecordByIndexOutOfBloons() throws ConfigurationException {
    BloonsTypeChain chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
    assertNull(chain.getBloonsTypeRecord(100));
  }

  @Test
  void testGetBloonsTypeRecordByNegativeIndex() throws ConfigurationException {
    BloonsTypeChain chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
    assertEquals(new BloonsType(chain, "DEAD",0, 0, Specials.None), chain.getBloonsTypeRecord(-1));
  }

  @Test
  void testGetNumNextBloons1() throws ConfigurationException {
    BloonsTypeChain chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
    assertEquals(1, chain.getNumNextBloons(chain.getBloonsTypeRecord("GREEN")));
  }

  @Test
  void testGetNumNextBloons2() throws ConfigurationException {
    BloonsTypeChain chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
    assertEquals(2, chain.getNumNextBloons(chain.getBloonsTypeRecord("RAINBOW")));
  }

}
