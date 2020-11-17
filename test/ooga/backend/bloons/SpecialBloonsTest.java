package ooga.backend.bloons;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ooga.backend.bloons.factory.CamoBloonsFactory;
import ooga.backend.bloons.factory.RegenBloonsFactory;
import ooga.backend.bloons.special.RegenBloon;
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
    assertTrue(bloon.getBloonsType().specials().contains(Specials.Camo));
  }

  @Test
  void testGetRegenBloonType() {
    Bloon bloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(bloon.getBloonsType().specials().contains(Specials.Regen));
  }

  @Test
  void testGetSpecialBloonTypeAfterHit() {
    Bloon bloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("BLUE"), 0, 0, 0, 0);
    Bloon[] bloons = bloon.shootBloon();
    assertEquals(chain.getBloonsTypeRecord("RED"), bloons[0].getBloonsType());
    assertTrue(bloons[0].getBloonsType().specials().contains(Specials.Regen));
  }

  @Test
  void testGetMultipleSpecialBloonsAfterHit() {
    Bloon bloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("ZEBRA"), 0, 0, 0, 0);
    Bloon[] bloons = bloon.shootBloon();
    assertEquals(chain.getBloonsTypeRecord("WHITE"), bloons[0].getBloonsType());
    assertEquals(chain.getBloonsTypeRecord("WHITE"), bloons[1].getBloonsType());
    assertTrue(bloons[0].getBloonsType().specials().contains(Specials.Regen));
    assertTrue(bloons[1].getBloonsType().specials().contains(Specials.Regen));
  }

  @Test
  void testGetMultipleSpecialBloonTypeAfterHit() {
    Bloon bloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("BLUE"), 0, 0, 0, 0);
    bloon = new CamoBloonsFactory().createBloon(bloon);
    Bloon[] bloons = bloon.shootBloon();
    assertEquals(chain.getBloonsTypeRecord("RED"), bloons[0].getBloonsType());
    assertTrue(bloons[0].getBloonsType().specials().contains(Specials.Regen));
    assertTrue(bloons[0].getBloonsType().specials().contains(Specials.Camo));
  }

  @Test
  void testGetSpecialBloonNextType() {
    Bloon bloon = new CamoBloonsFactory().createBloon(chain.getBloonsTypeRecord("YELLOW"), 0, 0, 0, 0);
    assertEquals(chain.getBloonsTypeRecord("GREEN"), chain.getNextBloonsType(bloon.getBloonsType()));
    assertTrue(bloon.getBloonsType().specials().contains(Specials.Camo));
  }

  @Test
  void testGetSpecialBloonPrevType() {
    Bloon bloon = new CamoBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertEquals(chain.getBloonsTypeRecord("BLUE"), chain.getPrevBloonsType(bloon.getBloonsType()));
    assertTrue(bloon.getBloonsType().specials().contains(Specials.Camo));
  }

  @Test
  void testMakeRegenBloonEstablishOriginalType() {
    Bloon regenBloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("PINK"), 0, 0, 0, 0);
    assertEquals(chain.getBloonsTypeRecord("PINK"), ((RegenBloon)regenBloon).getOriginalType());
  }

  @Test
  void testOriginalTypeNewBloonSpawn() {
    Bloon regenBloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("PINK"), 0, 0, 0, 0);
    Bloon[] spawnedBloons = regenBloon.shootBloon();
    assertEquals(chain.getBloonsTypeRecord("PINK"), ((RegenBloon)spawnedBloons[0]).getOriginalType());
    assertEquals(chain.getBloonsTypeRecord("YELLOW"), (spawnedBloons[0]).getBloonsType());
  }

}
