package backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;
import org.junit.jupiter.api.Test;

public class BloonsTypeReaderTest {

  @Test
  void testGetBloonsTypeRecordByName() {
    BloonsTypeChain chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
    assertEquals(new BloonsType("RED",1, 1), chain.getBloonsTypeRecord("RED"));
  }

  @Test
  void testGetNextBloonsTypeRecord() {
    BloonsTypeChain chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
    assertEquals(new BloonsType("DEAD",0, 0), chain.getNextBloonsType(chain.getBloonsTypeRecord("RED")));
  }

  @Test
  void testGetBloonsTypeRecordByIndex() {
    BloonsTypeChain chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
    assertEquals(new BloonsType("DEAD",0, 0), chain.getNextBloonsType(chain.getBloonsTypeRecord("RED")));
  }

}
