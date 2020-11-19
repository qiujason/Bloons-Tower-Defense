package ooga.backend.bloons;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ResourceBundle;
import ooga.backend.ConfigurationException;
import ooga.backend.bloons.factory.CamoBloonsFactory;
import ooga.backend.bloons.factory.RegenBloonsFactory;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.bloons.types.Specials;
import ooga.backend.bloons.factory.BasicBloonsFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BloonsTest {

  private static final String RESOURCE_BUNDLE_PATH = "bloon_resources/GameMechanics";
  private static final ResourceBundle GAME_MECHANICS = ResourceBundle.getBundle(RESOURCE_BUNDLE_PATH);

  private BloonsTypeChain chain;

  @BeforeEach
  void initializeBloonsTypes() throws ConfigurationException {
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
    assertSame(bloon.getBloonsType().specials(), (Specials.Camo));
  }

  @Test
  void testMakeRegenBloonFromFactory() {
    Bloon bloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertSame(bloon.getBloonsType().specials(), (Specials.Regen));
  }

  @Test
  void testShootBloon1Spawn() throws ConfigurationException {
    Bloon bloon = new BasicBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 0, 0);
    assertEquals(chain.getBloonsTypeRecord("DEAD"),bloon.shootBloon()[0].getBloonsType());
  }

  @Test
  void testShootBloonMultipleSpawn() throws ConfigurationException {
    Bloon bloon = new BasicBloonsFactory().createBloon(chain.getBloonsTypeRecord("RAINBOW"), 0, 0, 0, 0);
    assertEquals(2,bloon.shootBloon().length);
    assertEquals(chain.getBloonsTypeRecord("ZEBRA"),bloon.shootBloon()[0].getBloonsType());
    assertEquals(chain.getBloonsTypeRecord("ZEBRA"),bloon.shootBloon()[1].getBloonsType());
  }

  @Test
  void testPassDistanceTravelNewBasicBloon() {
    Bloon bloon = new BasicBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 10);
    bloon.update();
    double distanceTraveled = bloon.getDistanceTraveled();
    bloon = new BasicBloonsFactory().createBloon(bloon);
    assertEquals(distanceTraveled, bloon.getDistanceTraveled());
  }

  @Test
  void testPassDistanceTravelNewCamoBloon() {
    Bloon bloon = new CamoBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 10);
    bloon.update();
    double distanceTraveled = bloon.getDistanceTraveled();
    bloon = new CamoBloonsFactory().createBloon(bloon);
    assertEquals(distanceTraveled, bloon.getDistanceTraveled());
  }

  @Test
  void testPassDistanceTravelNewRegenBloon() {
    Bloon bloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 10);
    bloon.update();
    double distanceTraveled = bloon.getDistanceTraveled();
    bloon = new RegenBloonsFactory().createBloon(bloon);
    assertEquals(distanceTraveled, bloon.getDistanceTraveled());
  }

  @Test
  void testPassDistanceTravelNextBasicBloon() {
    Bloon bloon = new BasicBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 10);
    bloon.update();
    double distanceTraveled = bloon.getDistanceTraveled();
    bloon = new BasicBloonsFactory().createNextBloon(bloon);
    assertEquals(distanceTraveled, bloon.getDistanceTraveled());
  }

  @Test
  void testPassDistanceTravelNextCamoBloon() {
    Bloon bloon = new CamoBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 10);
    bloon.update();
    double distanceTraveled = bloon.getDistanceTraveled();
    bloon = new CamoBloonsFactory().createNextBloon(bloon);
    assertEquals(distanceTraveled, bloon.getDistanceTraveled());
  }

  @Test
  void testPassDistanceTravelNextRegenBloon() {
    Bloon bloon = new RegenBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 10);
    bloon.update();
    double distanceTraveled = bloon.getDistanceTraveled();
    bloon = new RegenBloonsFactory().createNextBloon(bloon);
    assertEquals(distanceTraveled, bloon.getDistanceTraveled());
  }

  @Test
  void testSlowDownBloon() {
    Bloon bloon = new BasicBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 10);
    bloon.slowDown();
    bloon.update();
    double expectedDistance = (Math.abs(bloon.getXVelocity()) + Math.abs(bloon.getYVelocity())) * chain.getBloonsTypeRecord("RED").relativeSpeed() * Double.parseDouble(GAME_MECHANICS.getString("SlowDownSpeedFactor"));
    assertEquals(expectedDistance, bloon.getDistanceTraveled());
  }

  @Test
  void testFreezeBloon() {
    Bloon bloon = new BasicBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 10);
    bloon.freeze();
    bloon.update();
    assertEquals(0, bloon.getDistanceTraveled());
  }

  @Test
  void testSlowDownThenFreezeBloon() {
    Bloon bloon = new BasicBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 10);
    bloon.slowDown();
    bloon.update();
    double expectedDistance = (Math.abs(bloon.getXVelocity()) + Math.abs(bloon.getYVelocity())) * chain.getBloonsTypeRecord("RED").relativeSpeed() * Double.parseDouble(GAME_MECHANICS.getString("SlowDownSpeedFactor"));
    assertEquals(expectedDistance, bloon.getDistanceTraveled());
    bloon.freeze();
    bloon.update();
    assertEquals(expectedDistance, bloon.getDistanceTraveled());
  }

  @Test
  void testFreezeThenSlowDownBloon() {
    Bloon bloon = new BasicBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 10);
    bloon.freeze();
    bloon.update();
    bloon.slowDown();
    bloon.update();
    assertEquals(0, bloon.getDistanceTraveled());
  }

  @Test
  void testSlowDownBloonTimer() {
    Bloon bloon = new BasicBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 10);
    bloon.slowDown();
    double expectedDistance = 0;
    for (int i = 0; i < Integer.parseInt(GAME_MECHANICS.getString("SlowDownTimePeriod")); i++) {
      bloon.update();
      expectedDistance += (Math.abs(bloon.getXVelocity()) + Math.abs(bloon.getYVelocity())) * chain.getBloonsTypeRecord("RED").relativeSpeed() *
          Double.parseDouble(GAME_MECHANICS.getString("SlowDownSpeedFactor"));
      assertEquals(expectedDistance, bloon.getDistanceTraveled());
    }
    bloon.update();
    assertEquals(30, bloon.getDistanceTraveled());
  }

  @Test
  void testFreezeBloonTimer() {
    Bloon bloon = new BasicBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 10);
    bloon.freeze();
    for (int i = 0; i < Integer.parseInt(GAME_MECHANICS.getString("FreezeTimePeriod")); i++) {
      bloon.update();
      assertEquals(0, bloon.getDistanceTraveled());
    }
    bloon.update();
    assertEquals(0, bloon.getDistanceTraveled());
  }

  @Test
  void testSlowDownThenSlowDownBloonTimerNotReset() {
    Bloon bloon = new BasicBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 10);
    bloon.slowDown();
    double expectedDistance = 0;
    for (int i = 0; i < Integer.parseInt(GAME_MECHANICS.getString("SlowDownTimePeriod")); i++) {
      bloon.slowDown();
      bloon.update();
      expectedDistance += (Math.abs(bloon.getXVelocity()) + Math.abs(bloon.getYVelocity())) * chain.getBloonsTypeRecord("RED").relativeSpeed() *
          Double.parseDouble(GAME_MECHANICS.getString("SlowDownSpeedFactor"));
      assertEquals(expectedDistance, bloon.getDistanceTraveled());
    }
    bloon.update();
   assertEquals(30, bloon.getDistanceTraveled());
  }

  @Test
  void testFreezeThenFreezeBloonTimerNotReset() {
    Bloon bloon = new BasicBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 0, 0, 10, 10);
    bloon.freeze();
    double expectedDistance = 20;
    for (int i = 0; i < Integer.parseInt(GAME_MECHANICS.getString("FreezeTimePeriod")); i++) {
      bloon.freeze();
      bloon.update();
      assertEquals(0, bloon.getDistanceTraveled());
    }
    bloon.update();
    assertEquals(0, bloon.getDistanceTraveled());
  }

}
