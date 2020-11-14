package ooga.backend.bloons;

import static org.junit.jupiter.api.Assertions.assertTrue;

import ooga.backend.bloons.factory.CamoBloonsFactory;
import ooga.backend.bloons.factory.RegenBloonsFactory;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.bloons.types.Specials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpecialBloonsTest {

  private BloonsTypeChain chain;

  @BeforeEach
  void initializeBloonsTypes() {
    chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
  }

  @Test
  void testGetCamoBloonType() {
    Bloon bloon = new CamoBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(bloon.getBloonsType().specials().contains(Specials.CAMO));
  }

  @Test
  void testGetRegenBloonType() {
    Bloon bloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(bloon.getBloonsType().specials().contains(Specials.REGEN));
  }

//  @Test
//  void testGetRegenBloonType() {
//    Bloon bloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
//    assertTrue(bloon.getBloonsType().specials().contains(Specials.REGEN));
//  }

}
