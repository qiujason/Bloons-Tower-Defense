package ooga.backend.bloons;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ResourceBundle;
import ooga.backend.ConfigurationException;
import ooga.backend.bloons.factory.CamoBloonsFactory;
import ooga.backend.bloons.factory.RegenBloonsFactory;
import ooga.backend.bloons.special.CamoBloon;
import ooga.backend.bloons.special.RegenBloon;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.bloons.types.Specials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpecialBloonsTest {

  private static final String RESOURCE_BUNDLE_PATH = "bloon_resources/GameMechanics";
  private static final ResourceBundle GAME_MECHANICS = ResourceBundle.getBundle(RESOURCE_BUNDLE_PATH);

  private BloonsTypeChain chain;

  @BeforeEach
  void initializeBloonsTypes() throws ConfigurationException {
    chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
  }

  @Test
  void testGetCamoBloonType() {
    Bloon bloon = new CamoBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertSame(bloon.getBloonsType().specials(), (Specials.Camo));
  }

  @Test
  void testGetRegenBloonType() {
    Bloon bloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertSame(bloon.getBloonsType().specials(), (Specials.Regen));
  }

  @Test
  void testGetSpecialBloonTypeAfterHit() throws ConfigurationException {
    Bloon bloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("BLUE"), 0, 0, 0, 0);
    Bloon[] bloons = bloon.shootBloon();
    assertEquals(chain.getBloonsTypeRecord("RED"), bloons[0].getBloonsType());
    assertTrue(bloons[0] instanceof RegenBloon);
    assertSame(bloons[0].getBloonsType().specials(), (Specials.Regen));
  }

  @Test
  void testGetMultipleSpecialBloonsAfterHit() throws ConfigurationException {
    Bloon bloon = new CamoBloonsFactory().createBloon(chain.getBloonsTypeRecord("ZEBRA"), 0, 0, 0, 0);
    Bloon[] bloons = bloon.shootBloon();
    assertEquals(chain.getBloonsTypeRecord("WHITE"), bloons[0].getBloonsType());
    assertEquals(chain.getBloonsTypeRecord("WHITE"), bloons[1].getBloonsType());
    assertTrue(bloons[0] instanceof CamoBloon);
    assertTrue(bloons[1] instanceof CamoBloon);
    assertSame(bloons[0].getBloonsType().specials(), (Specials.Camo));
    assertSame(bloons[1].getBloonsType().specials(), (Specials.Camo));
  }

  @Test
  void testGetSpecialBloonNextType() {
    Bloon bloon = new CamoBloonsFactory().createBloon(chain.getBloonsTypeRecord("YELLOW"), 0, 0, 0, 0);
    assertEquals(chain.getBloonsTypeRecord("GREEN"), chain.getNextBloonsType(bloon.getBloonsType()));
    assertSame(bloon.getBloonsType().specials(), (Specials.Camo));
  }

  @Test
  void testGetSpecialBloonPrevType() {
    Bloon bloon = new CamoBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertEquals(chain.getBloonsTypeRecord("BLUE"), chain.getPrevBloonsType(bloon.getBloonsType()));
    assertSame(bloon.getBloonsType().specials(), (Specials.Camo));
  }

  @Test
  void testMakeRegenBloonEstablishOriginalType() {
    Bloon regenBloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("PINK"), 0, 0, 0, 0);
    assertEquals(chain.getBloonsTypeRecord("PINK"), ((RegenBloon)regenBloon).getOriginalType());
  }

  @Test
  void testOriginalTypeNewBloonSpawn() throws ConfigurationException {
    Bloon regenBloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("PINK"), 0, 0, 0, 0);
    Bloon[] spawnedBloons = regenBloon.shootBloon();
    assertEquals(chain.getBloonsTypeRecord("PINK"), ((RegenBloon)spawnedBloons[0]).getOriginalType());
    assertEquals(chain.getBloonsTypeRecord("YELLOW"), (spawnedBloons[0]).getBloonsType());
  }

  @Test
  void testRegenPrevBloon() throws ConfigurationException {
    Bloon regenBloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("PINK"), 0, 0, 0, 0);
    Bloon[] spawnedBloons = regenBloon.shootBloon();
    for (int i = 0; i <= Integer.parseInt(GAME_MECHANICS.getString("RegrowthTimer")); i++) {
      assertEquals(chain.getBloonsTypeRecord("YELLOW"), (spawnedBloons[0]).getBloonsType());
      spawnedBloons[0].update();
    }
    assertEquals(chain.getBloonsTypeRecord("PINK"), (spawnedBloons[0]).getBloonsType());
  }

  @Test
  void testRegenPrevBloonIsRegen() throws ConfigurationException {
    Bloon regenBloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("PINK"), 0, 0, 0, 0);
    Bloon[] spawnedBloons = regenBloon.shootBloon();
    for (int i = 0; i <= Integer.parseInt(GAME_MECHANICS.getString("RegrowthTimer")); i++) {
      assertEquals(chain.getBloonsTypeRecord("YELLOW"), (spawnedBloons[0]).getBloonsType());
      spawnedBloons[0].update();
    }
    assertSame((spawnedBloons[0]).getBloonsType().specials(), (Specials.Regen));
  }

  @Test
  void testRegenPrevBloonLimitToOriginalType() throws ConfigurationException {
    Bloon regenBloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("PINK"), 0, 0, 0, 0);
    Bloon[] spawnedBloons = regenBloon.shootBloon();
    for (int i = 0; i <= Integer.parseInt(GAME_MECHANICS.getString("RegrowthTimer")); i++) {
      assertEquals(chain.getBloonsTypeRecord("YELLOW"), (spawnedBloons[0]).getBloonsType());
      spawnedBloons[0].update();
    }
    assertSame((spawnedBloons[0]).getBloonsType().specials(), (Specials.Regen));
    for (int i = 0; i <= Integer.parseInt(GAME_MECHANICS.getString("RegrowthTimer")); i++) {
      assertEquals(chain.getBloonsTypeRecord("PINK"), (spawnedBloons[0]).getBloonsType());
      spawnedBloons[0].update();
    }
    assertEquals(chain.getBloonsTypeRecord("PINK"), (spawnedBloons[0]).getBloonsType());
  }

  @Test
  void testRegenPrevBloonMultipleTimes() throws ConfigurationException {
    Bloon regenBloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("PINK"), 0, 0, 0, 0);
    Bloon[] spawnedBloons = regenBloon.shootBloon();
    spawnedBloons = spawnedBloons[0].shootBloon();
    for (int i = 0; i <= Integer.parseInt(GAME_MECHANICS.getString("RegrowthTimer")); i++) {
      assertEquals(chain.getBloonsTypeRecord("GREEN"), (spawnedBloons[0]).getBloonsType());
      spawnedBloons[0].update();
    }
    assertSame((spawnedBloons[0]).getBloonsType().specials(), (Specials.Regen));
    for (int i = 0; i <= Integer.parseInt(GAME_MECHANICS.getString("RegrowthTimer")); i++) {
      assertEquals(chain.getBloonsTypeRecord("YELLOW"), (spawnedBloons[0]).getBloonsType());
      spawnedBloons[0].update();
    }
    assertEquals(chain.getBloonsTypeRecord("PINK"), (spawnedBloons[0]).getBloonsType());
  }

}
