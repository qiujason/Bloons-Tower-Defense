package ooga.backend.bloons;

import static org.junit.jupiter.api.Assertions.*;

import ooga.backend.bloons.factory.CamoBloonsFactory;
import ooga.backend.bloons.factory.RegenBloonsFactory;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.bloons.types.Specials;
import ooga.backend.bloons.factory.BasicBloonsFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BloonsTest {

  private BloonsTypeChain chain;

  @BeforeEach
  void initializeBloonsTypes() {
    chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
  }

  @Test
  void testCreateBloonsFromFactory() {
    assertNotNull((new BasicBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0)));
  }

  @Test
  void testBloonsPositionUpdate() {
    Bloon bloon = new Bloon(chain.getBloonsTypeRecord("RED"), 10, 10, 10, 10);
    bloon.update();
    assertEquals(20, bloon.getXPosition());
    assertEquals(20, bloon.getYPosition());
  }

  @Test
  void testBloonsDistanceTraveled() {
    Bloon bloon = new Bloon(chain.getBloonsTypeRecord("RED"), 10, 10, 10, 10);
    bloon.update();
    assertEquals(20, bloon.getDistanceTraveled());
  }

  @Test
  void testBloonsChangedVelocityPositionUpdate() {
    Bloon bloon = new Bloon(chain.getBloonsTypeRecord("RED"), 10, 10, 10, 10);
    bloon.update();
    assertEquals(20, bloon.getXPosition());
    assertEquals(20, bloon.getYPosition());
    bloon.setXVelocity(-5);
    bloon.update();
    assertEquals(15, bloon.getXPosition());
    assertEquals(30, bloon.getYPosition());
    bloon.setYVelocity(20);
    bloon.update();
    assertEquals(10, bloon.getXPosition());
    assertEquals(50, bloon.getYPosition());
  }

  @Test
  void testMakeCamoBloonFromFactory() {
    Bloon bloon = new CamoBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(bloon.getBloonsType().specials().contains(Specials.Camo));
  }

  @Test
  void testMakeRegenBloonFromFactory() {
    Bloon bloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertTrue(bloon.getBloonsType().specials().contains(Specials.Regen));
  }

  @Test
  void testMakeCamoRegenBloonFromFactory() {
    Bloon bloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    bloon = new CamoBloonsFactory().createBloon(bloon);
    assertTrue(bloon.getBloonsType().specials().contains(Specials.Regen));
    assertTrue(bloon.getBloonsType().specials().contains(Specials.Camo));
  }

  @Test
  void testShootBloon1Spawn() {
    Bloon bloon = new BasicBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertEquals(chain.getBloonsTypeRecord("DEAD"),bloon.shootBloon()[0].getBloonsType());
  }

  @Test
  void testShootBloonMultipleSpawn() {
    Bloon bloon = new BasicBloonsFactory().createBloon(chain.getBloonsTypeRecord("RAINBOW"), 0, 0, 0, 0);
    assertEquals(2,bloon.shootBloon().length);
    assertEquals(chain.getBloonsTypeRecord("ZEBRA"),bloon.shootBloon()[0].getBloonsType());
    assertEquals(chain.getBloonsTypeRecord("ZEBRA"),bloon.shootBloon()[1].getBloonsType());
  }

}
