package backend;

import static org.junit.jupiter.api.Assertions.*;

import ooga.backend.bloons.BasicBloons;
import ooga.backend.bloons.Bloons;
import ooga.backend.factory.BasicBloonsFactory;
import org.junit.jupiter.api.Test;


public class BloonsTest {

  @Test
  void testCreateBloonsFromFactory() {
    assertTrue((new BasicBloonsFactory().createBloons(0, 0, 0, 0, 0)) instanceof BasicBloons);
  }

  @Test
  void testDecrementLife() {
    Bloons bloon = new BasicBloonsFactory().createBloons(1, 0, 0, 0, 0);
    bloon.decrementLives(1);
    assertEquals(0, bloon.getLives());
  }

  @Test
  void testBloonsPositionUpdate() {
    Bloons bloon = new BasicBloonsFactory().createBloons(0, 10, 10, 10, 10);
    bloon.updatePosition();
    assertEquals(20, bloon.getXPosition());
    assertEquals(20, bloon.getYPosition());
  }

  @Test
  void testBloonsChangedVelocityPositionUpdate() {
    Bloons bloon = new BasicBloonsFactory().createBloons(0, 10, 10, 10, 10);
    bloon.updatePosition();
    assertEquals(20, bloon.getXPosition());
    assertEquals(20, bloon.getYPosition());
    bloon.setXVelocity(-5);
    bloon.updatePosition();
    assertEquals(15, bloon.getXPosition());
    assertEquals(30, bloon.getYPosition());
    bloon.setYVelocity(20);
    bloon.updatePosition();
    assertEquals(10, bloon.getXPosition());
    assertEquals(50, bloon.getYPosition());
  }

}
